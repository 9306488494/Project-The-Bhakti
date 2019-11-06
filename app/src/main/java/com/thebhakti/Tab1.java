package com.thebhakti;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thebhakti.Interface.ILoadMore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment {
    private RecyclerView ganesh_recView;

    private ArrayList<Ganesha> ganeshaList1;
    public static FirebaseStorage fs;
    public static StorageReference sr;
    public StorageReference sr2;
    FirebaseFirestore db;
    CollectionReference cr;
    private String image_name,Title,description1,documentid,DespTitle;
  /*  private AdView adView;*/

    FirebaseAuth mAuth;

// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ganeshaList1=new ArrayList<>();
        /*shivaList1.add(new Shiva(R.drawable.comment,R.drawable.app_logo_old));*/
      /*  Shiva shiva=new Shiva(val);
        shivaList1.add(shiva);*/



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tab0, container, false);
        ganesh_recView = (RecyclerView) view.findViewById(R.id.ganesh_recView);

       /* // FB native ads Banner
        adView = new com.facebook.ads.AdView(getContext(), "539967309761431_541729399585222", AdSize.RECTANGLE_HEIGHT_250);
        // Request an ad
        adView.loadAd();
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) view.findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // FB Native ads complete
*/

        //Initilize Firebase Firestore for image
        db=FirebaseFirestore.getInstance();
         // for file path
        // Instantiate the String Request.
        RequestQueue queue= Volley.newRequestQueue(getContext());
        String Url="http://shivagod.in/Prabhu/astrology.php";
        StringRequest request=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(request);
        super.onStart();
        return view;

    }

    private void parseData(String response) { try {
        // Create JSOn Object
        JSONArray jsonArray = new JSONArray(response);


        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            documentid="shiva_id";
            image_name=jsonObject.getString("image");
            Title="The Bhakti";
            description1=jsonObject.getString("timestamp");
            DespTitle=jsonObject.getString("name");
            String MetaData="http://shivagod.in/Prabhu/astrology.php";
            String Product="no_product";

            // Shiva
            Ganesha ganesha=new Ganesha(DespTitle,description1,documentid,Title,image_name,MetaData,Product);
            ganeshaList1.add(ganesha);

            final RecyclerAdapterGanesha recyclerAdapterGanesha=new RecyclerAdapterGanesha(getContext(),ganeshaList1);
            ganesh_recView.setAdapter(recyclerAdapterGanesha);
            ganesh_recView.setHasFixedSize(true);
            ganesh_recView.setLayoutManager(new LinearLayoutManager(getActivity()));


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
