package com.example.android.insignia2k16;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SponsorsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SponsorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SponsorsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView listView;

    public SponsorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SponsorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SponsorsFragment newInstance(String param1, String param2) {
        SponsorsFragment fragment = new SponsorsFragment();
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
        View root= inflater.inflate(R.layout.fragment_sponsors, container, false);
        listView=(ListView)root.findViewById(R.id.lView_sponsors);
        int[] data={R.drawable.cisco, R.drawable.coke, R.drawable.hero, R.drawable.mtv, R.drawable.toi};
        List list=new ArrayList();
        for(int i=0;i<data.length;i++){
            list.add(data[i]);
        }
        Log.d("xxxx",list+"");
        MyCustomAdapter adapter=new MyCustomAdapter(getContext(), R.layout.sponsors_row,list,data);
        listView.setAdapter(adapter);
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    class MyCustomAdapter extends ArrayAdapter{
        Context context;
        int[] imageArray;
        MyCustomAdapter(Context c,int x,List images,int[] array){
            super(c,x, R.id.img_sponsors,images);
            context=c;
            imageArray=array;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v=convertView;
            if(v==null){
                LayoutInflater i=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v=i.inflate(R.layout.sponsors_row, parent,false);
            }

            YoYo.with(Techniques.FlipInX)
                    .duration(700)
                    .playOn(v.findViewById(R.id.img_sponsors));

            ImageView i=(ImageView) v.findViewById(R.id.img_sponsors);
            i.setImageResource(imageArray[position]);
            if(position==4){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)((LinearLayout)v.findViewById(R.id.sponsors)).getLayoutParams();
                params.setMargins(0, 0, 0, 200);
                ((LinearLayout)v.findViewById(R.id.sponsors)).setLayoutParams(params);
            }
            if(position==0){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)((LinearLayout)v.findViewById(R.id.sponsors)).getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                ((LinearLayout)v.findViewById(R.id.sponsors)).setLayoutParams(params);
            }
            return v;
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
