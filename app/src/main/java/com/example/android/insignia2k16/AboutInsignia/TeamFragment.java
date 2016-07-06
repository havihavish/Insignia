package com.example.android.insignia2k16.AboutInsignia;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.insignia2k16.R;

public class TeamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ListView listView;
    RecyclerView recyclerView;
    int image[]={R.raw.contimg1, R.raw.contimg1, R.raw.contimg1, R.raw.contimg1, R.raw.contimg1, R.raw.contimg1, R.raw.contimg1};
    String name[]={"Chamarthi Aditya","Chamarthi Aditya","Chamarthi Aditya","Chamarthi Aditya","Chamarthi Aditya","Chamarthi Aditya","Chamarthi Aditya"};
    String post[]={"fest organiser","fest organiser","fest organiser","fest organiser","fest organiser","fest organiser","fest organiser"};
    String mail[]={"insignia@nitdelhi.ac.in","insignia@nitdelhi.ac.in","insignia@nitdelhi.ac.in","insignia@nitdelhi.ac.in","insignia@nitdelhi.ac.in","insignia@nitdelhi.ac.in","insignia@nitdelhi.ac.in"};

    public TeamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamFragment newInstance(String param1, String param2) {
        TeamFragment fragment = new TeamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_team, container, false);
        //listView=(ListView)root.findViewById(R.id.lView_team);
        listView=(ListView) root.findViewById(R.id.rView_team);
        MyCustomAdapter adapter=new MyCustomAdapter(getContext(),name,image,post,mail);
        listView.setAdapter(adapter);
        return root;
    }

    class MyCustomAdapter extends ArrayAdapter {
        Context context;
        int[] imageArray;
        String[] name;
        String[] positions;
        String[] email;
        MyCustomAdapter(Context c,  String names[], int[] images,String post[],String mail[]){
            super(c, R.layout.team_row,names);
            context=c;
            name=names;
            imageArray=images;
            positions=post;
            email=mail;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v=convertView;
            if(v==null){
                LayoutInflater i=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v=i.inflate(R.layout.team_row, parent,false);
            }

//            YoYo.with(Techniques.FlipInX)
//                    .duration(700)
//                    .playOn(v.findViewById(R.id.img_sponsors));

            ImageView i=(ImageView) v.findViewById(R.id.contImg);
            i.setImageResource(imageArray[position]);
            TextView N=(TextView)v.findViewById(R.id.name);
            N.setText(name[position]);
            TextView P=(TextView)v.findViewById(R.id.post);
            P.setText(positions[position]);
            TextView E=(TextView)v.findViewById(R.id.mail);
            E.setText(email[position]);
            if(position==5){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)((LinearLayout)v.findViewById(R.id.team)).getLayoutParams();
                params.setMargins(0, 0, 0, 200);
                ((LinearLayout)v.findViewById(R.id.team)).setLayoutParams(params);
            }
            if(position==0){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)((LinearLayout)v.findViewById(R.id.team)).getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                ((LinearLayout)v.findViewById(R.id.team)).setLayoutParams(params);
            }
            return v;
        }

        @Override
        public int getCount() {
            return 6;
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
