package com.example.android.insignia2k16.Instafeed;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vengalrao on 10-07-2016.
 */
public class InstagramApp {
    private InstagramSession mSession;
    private InstagramDialog mDialog;
    private OAuthAuthenticationListener mListener;
    private ProgressDialog mProgress;
    private String mAuthUrl;
    private String mTokenUrl;
    private String mAccessToken;
    private Context mCtx;
    private String mClientId;
    private String mClientSecret;

    private static int WHAT_FINALIZE = 0;
    private static int WHAT_ERROR = 1;
    private static int WHAT_FETCH_INFO = 2;

    /**
     * Callback url, as set in 'Manage OAuth Costumers' page
     * (https://developer.github.com/)
     */

    public static String mCallbackUrl = "";
    private static final String AUTH_URL = "https://api.instagram.com/oauth/authorize/";
    private static final String TOKEN_URL = "https://api.instagram.com/oauth/access_token";
    private static final String API_URL = "https://api.instagram.com/v1";
    private static final String LIKE_URL="https://api.instagram.com/v1/media/";
    private static final String COMMENT_URL="https://api.instagram.com/v1/media/";
    private static final String TAG = "InstagramAPI";
    List list=new ArrayList<>();
    List media_id=new ArrayList();
    List checkLiked=new ArrayList();

    public InstagramApp(Context context, String clientId, String clientSecret,
                        String callbackUrl) {
        mClientId = clientId;
        mClientSecret = clientSecret;
        mCtx = context;
        mSession = new InstagramSession(context);
        mAccessToken = mSession.getAccessToken();
        mCallbackUrl = callbackUrl;
        mTokenUrl = TOKEN_URL + "?client_id=" + clientId + "&client_secret="
                + clientSecret + "&redirect_uri=" + mCallbackUrl + "&grant_type=authorization_code";
        mAuthUrl = AUTH_URL + "?client_id=" + clientId + "&redirect_uri="
                + mCallbackUrl + "&response_type=code&display=touch&scope=public_content+likes+comments+relationships";
        InstagramDialog.OAuthDialogListener listener = new InstagramDialog.OAuthDialogListener() {
            @Override
            public void onComplete(String code) {
                getAccessToken(code);
            }

            @Override
            public void onError(String error) {
                mListener.onFail("Authorization failed");
            }
        };
        mDialog = new InstagramDialog(context, mAuthUrl, listener);
        mProgress = new ProgressDialog(context);
        mProgress.setCancelable(false);
        mDialog.show();
    }

    public List getList(){
        return list;
    }

    public List getWhetherLiked(){
        return checkLiked;
    }
    public void likes(int  index){
        LikeTask likeTask=new LikeTask(index);
        likeTask.execute();
    }
    public void comments(int index,String comment){
        CommentTask task=new CommentTask(index,comment);
        task.execute();
    }
    class CommentTask extends AsyncTask<String,String,String>{
        int ind;
        String text;
        CommentTask(int index,String comment){
            ind=index;
            text=comment;
        }
        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(LIKE_URL+(String)media_id.get(ind)+"/comments");
                Log.i(TAG, "Opening Token URL " + url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write("&access_token="+mAccessToken+
                        "&text="+text
                );
                writer.flush();
                String response = streamToString(urlConnection.getInputStream());
                Log.i(TAG, "response " + response);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    class LikeTask extends AsyncTask<String,String,String>{
        int ind;
        LikeTask(int index)
        {
            ind=index;
        }

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(LIKE_URL+(String)media_id.get(ind)+"/likes");
                Log.i(TAG, "Opening Token URL " + url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write("&access_token="+mAccessToken
                               );
                writer.flush();
                String response = streamToString(urlConnection.getInputStream());
                Log.i(TAG, "response " + response);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
    private void getAccessToken(final String code) {
        mProgress.setMessage("Getting access ...");
        mProgress.show();

        Thread t=new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "Getting access token");
                int what = WHAT_FETCH_INFO;
                try {
                    URL url = new URL(TOKEN_URL);
                    Log.i(TAG, "Opening Token URL " + url.toString());
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write("client_id="+mClientId+
                            "&client_secret="+mClientSecret+
                            "&grant_type=authorization_code" +
                            "&redirect_uri="+mCallbackUrl+
                            "&code=" +code );
                    writer.flush();
                    String response = streamToString(urlConnection.getInputStream());
                    Log.i(TAG, "response " + response);
                    JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
                    mAccessToken = jsonObj.getString("access_token");
                    Log.i(TAG, "Got access token: " + mAccessToken);
                    Log.d("xxxxxxxx","3");
                    String id = jsonObj.getJSONObject("user").getString("id");
                    String user = jsonObj.getJSONObject("user").getString("username");
                    String name = jsonObj.getJSONObject("user").getString("full_name");
                    mSession.storeAccessToken(mAccessToken, id, user, name);
                    fetchData();
                } catch (Exception ex) {
                    what = WHAT_ERROR;
                    ex.printStackTrace();
                }

                //mHandler.sendMessage(mHandler.obtainMessage(what, 1, 0));
            }
        };
        t.start();

    }
    public void fetchData() {
        //mProgress.setMessage("Finalizing ...");

        new Thread() {
            @Override
            public void run() {
                Log.d("xxxxxxxx","11");
                Log.i(TAG, "Fetching user info");
                int what = WHAT_FINALIZE;
                try {
                    //URL url = new URL(API_URL + "/users/" + "3284788519" + "/?access_token=" + mAccessToken);
                    URL url=new URL("https://api.instagram.com/v1/users/"+"3540791136"+"/media/recent/?access_token="+mAccessToken);
                    Log.d(TAG, "Opening URL " + url.toString());
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoInput(true);
                    urlConnection.connect();
                    String response = streamToString(urlConnection.getInputStream());
                    System.out.println(response);
                    JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
                    JSONArray jsonArray=jsonObj.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++){
                        String media=jsonArray.getJSONObject(i).getString("id");
                        String img=jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        list.add(img);
                        media_id.add(media);
                    }

                    userLiked(media_id);
                    Log.d("yyyy",(String) list.get(0));
                    mProgress.dismiss();
                } catch (Exception ex) {
                    what = WHAT_ERROR;
                    ex.printStackTrace();
                }

            }
        }.start();
    }

    public void userLiked(List list){
        for(int i=0;i<list.size();i++){
            String media_id=(String)list.get(i);
            URL url= null;
            try {
                url = new URL("https://api.instagram.com/v1/media/"+media_id+"?access_token="+mAccessToken);
                //Log.d(TAG, "Opening URL " + url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();
                String response = streamToString(urlConnection.getInputStream());
                System.out.println(response);
                JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
                JSONObject jsonObject=jsonObj.getJSONObject("data");
                String bool=jsonObject.getString("user_has_liked");
                if(bool.equals("false")){
                    checkLiked.add(false);
                }else{
                    checkLiked.add(true);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        mListener.onSuccess();
    }

    public boolean hasAccessToken() {
        return (mAccessToken == null) ? false : true;
    }

    public void setListener(OAuthAuthenticationListener listener) {
        mListener = listener;
    }

    public String getUserName() {
        return mSession.getUsername();
    }

    public String getId() {
        return mSession.getId();
    }
    public String getName() {
        return mSession.getName();
    }
    public void authorize() {
        //Intent webAuthIntent = new Intent(Intent.ACTION_VIEW);
        //webAuthIntent.setData(Uri.parse(AUTH_URL));
        //mCtx.startActivity(webAuthIntent);
        mDialog.show();
    }

    private String streamToString(InputStream is) throws IOException {
        String str = "";

        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();
            } finally {
                is.close();
            }

            str = sb.toString();
        }

        return str;
    }

    public void resetAccessToken() {
        if (mAccessToken != null) {
            mSession.resetAccessToken();
            mAccessToken = null;
        }
    }

    public interface OAuthAuthenticationListener {
        public abstract void onSuccess();

        public abstract void onFail(String error);
    }
}
