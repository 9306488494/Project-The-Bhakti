package com.thebhakti;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.facebook.ads.*;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab3 extends Fragment {
    private RecyclerView playlistRec;
    private ArrayList<Playlst> playList1;
    public static FirebaseStorage fs;
    public static StorageReference sr;
    public StorageReference sr2;
    FirebaseFirestore db;
    CollectionReference cr;
    String Url;
    private String image_name,playlist_id,Channel_id,video_id,Video_title,MetaData,Channel_name;
    /*private AdView adView;*/
    String url1="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLdsRVpbMW_KHq4i1IhIMJrj2oNa-Uz-p5&key=AIzaSyCI1oCTXwZzgVv7LDQ8NykSIUEWt247KnU&maxResults=50";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab3 newInstance(String param1, String param2) {
        Tab3 fragment = new Tab3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playList1=new ArrayList<>();
        // Add data into list
        /*Krishana krishana=new Krishana(val);
        krishanaList1.add(krishana);*/

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.playlist_lay, container, false);
        playlistRec = (RecyclerView) view.findViewById(R.id.playlist_rec);

     /*   // FB native ads Banner
        adView = new AdView(getContext(), "539967309761431_541729399585222", AdSize.RECTANGLE_HEIGHT_250);
        // Request an ad
        adView.loadAd();
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) view.findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // FB Native ads complete*/


//Initilize Firebase Firestore for image
        // Instantiate the String Request.
        RequestQueue queue= Volley.newRequestQueue(getContext());
         Url=url1;

        StringRequest request=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);

        super.onStart();

        return view;
    }

    private void parseData(String response) {
        try {
            // Create JSOn Object
            JSONObject jsonObject = new JSONObject(response);

            JSONArray jsonArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                // JSON Objects complete now Collect JSON Array Data which we need
                Video_title=jsonObject1.getJSONObject("snippet").getString("title");
                Channel_id=jsonObject1.getJSONObject("snippet").getString("channelId");
                video_id=jsonObject1.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                playlist_id=jsonObject1.getJSONObject("snippet").getString("playlistId");
                image_name=jsonObject1.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                Channel_name=jsonObject1.getJSONObject("snippet").getString("channelTitle");


                MetaData = url1;



                //Youtube
                Playlst playlst = new Playlst(Video_title, Channel_id, video_id, playlist_id, image_name, MetaData, Channel_name,url1);
                playList1.add(playlst);


                RecyclerAdapterPlayLst recyclerAdapterPlayLst = new RecyclerAdapterPlayLst(getContext(), playList1);
                playlistRec.setHasFixedSize(true);
                playlistRec.setLayoutManager(new LinearLayoutManager(getActivity()));
                playlistRec.setAdapter(recyclerAdapterPlayLst);


            }
        } catch (JSONException e) {
            e.printStackTrace();
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
