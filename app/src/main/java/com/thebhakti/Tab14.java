package com.thebhakti;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import com.facebook.ads.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab14.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab14#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab14 extends Fragment {

    private RecyclerView list1Rec;
    ArrayList<Plist1>plist1;
    String Video_title, Channel_id, video_id, playlist_id, image_name, MetaData, Channel_name;
    String Video_title1, Channel_id1, video_id1, playlist_id1, image_name1, MetaData1, Channel_name1;
    String Video_title2, Channel_id2, video_id2, playlist_id2, image_name2, MetaData2, Channel_name2;
    String Video_title3, Channel_id3, video_id3, playlist_id3, image_name3, MetaData3, Channel_name3;
    private RecyclerView list2Rec;
    private RecyclerView list3Rec;
    private RecyclerView list4Rec;
    ArrayList<PL2>pList2;
    ArrayList<PL3>pList3;
    ArrayList<PL4>pList4;
    private RecyclerView ganesh_recView;
    private ArrayList<Ganesha> ganeshaList1;
    private ArrayList<Ganesha2> ganeshaList2;
    private LinearLayout bannerContainer;

    AdView adView;
    private RecyclerView ganeshRecView2;
    private RecyclerView ganeshRecView3;
    private RecyclerView ganeshRecView4;

    private String image1,Title,description1,documentid,DespTitle;
    private String image2,Title2,description2,documentid2,DespTitle2;
    private String image3,Title3,description3,documenti3,DespTitle3;
    private String image4,Title4,description4,documentid4,DespTitle4;

    private ImageView chatBanner;


 // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab14() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab14.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab14 newInstance(String param1, String param2) {
        Tab14 fragment = new Tab14();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     plist1=new ArrayList<>();
        pList2=new ArrayList<>();
        pList3=new ArrayList<>();
        pList4=new ArrayList<>();
        ganeshaList1=new ArrayList<>();
        ganeshaList2=new ArrayList<>();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tab15, container, false);
        list2Rec = (RecyclerView) view.findViewById(R.id.list2_rec);
        list3Rec = (RecyclerView) view.findViewById(R.id.list3_rec);
        list4Rec = (RecyclerView) view.findViewById(R.id.list4_rec);
        list1Rec = (RecyclerView) view.findViewById(R.id.list1_rec);
        ganeshRecView2 = (RecyclerView) view.findViewById(R.id.ganesh_recView2);
      /*  ganeshRecView3 = (RecyclerView) view.findViewById(R.id.ganesh_recView3);
        ganeshRecView4 = (RecyclerView) view.findViewById(R.id.ganesh_recView4);*/
        ganesh_recView = (RecyclerView) view.findViewById(R.id.ganesh_recView);
        chatBanner = (ImageView) view.findViewById(R.id.chatBanner);

        // FB native ads Banner
        adView = new AdView(getContext(), "539967309761431_541729399585222", AdSize.RECTANGLE_HEIGHT_250);
        // Request an ad
        adView.loadAd();
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) view.findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // FB Native ads complete


        // Fetch data from youtube library for Trending Videos playlist 1
        RequestQueue queue= Volley.newRequestQueue(getContext());
        String Url="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLX87es2YBP1u_zNMIH30H3uUFlhhG4eGn&key=AIzaSyCI1oCTXwZzgVv7LDQ8NykSIUEWt247KnU&maxResults=50";
        StringRequest request=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseData1(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Network Issue", Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout=30000;
        RetryPolicy policy=new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
        super.onStart();

        // Fetch data from youtube library for Popular Videos playlist 2
        RequestQueue queue2= Volley.newRequestQueue(getContext());
        String Url2="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLX87es2YBP1u7rQ4uZxtpbhWakGDQFzTC&key=AIzaSyCI1oCTXwZzgVv7LDQ8NykSIUEWt247KnU&maxResults=50";
        StringRequest request2=new StringRequest(Request.Method.GET, Url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                parseData2(response1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Network Issue", Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout2=30000;
        RetryPolicy policy2=new DefaultRetryPolicy(socketTimeout2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request2.setRetryPolicy(policy2);
        queue2.add(request2);
        super.onStart();

        // Fetch data from youtube library for Astrology Videos playlist 3
        RequestQueue queue3= Volley.newRequestQueue(getContext());
        String Url3="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLX87es2YBP1vGWKUV29ZqQ1ccAGZbI3WN&key=AIzaSyCI1oCTXwZzgVv7LDQ8NykSIUEWt247KnU&maxResults=50";
        StringRequest request3=new StringRequest(Request.Method.GET, Url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response3) {
                parseData3(response3);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Network Issue", Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout3=30000;
        RetryPolicy policy3=new DefaultRetryPolicy(socketTimeout3, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request3.setRetryPolicy(policy3);
        queue3.add(request3);
        super.onStart();

        // Fetch data from youtube library for Dharam Videos playlist 4
        RequestQueue queue4= Volley.newRequestQueue(getContext());
        String Url4="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLX87es2YBP1sY-KwFkLsB6hCtNy53qgCJ&key=AIzaSyCI1oCTXwZzgVv7LDQ8NykSIUEWt247KnU&maxResults=50";
        StringRequest request4=new StringRequest(Request.Method.GET, Url4, new Response.Listener<String>() {
            @Override
            public void onResponse(String response4) {
                parseData4(response4);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Network Issue", Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout4=30000;
        RetryPolicy policy4=new DefaultRetryPolicy(socketTimeout4, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request4.setRetryPolicy(policy4);
        queue4.add(request4);
        super.onStart();

        // Instantiate the String Request for Astrology for front page.
        RequestQueue queue5= Volley.newRequestQueue(getContext());
        String Url5="http://shivagod.in/Prabhu/astrology.php";
        StringRequest request5=new StringRequest(Request.Method.GET, Url5, new Response.Listener<String>() {
            @Override
            public void onResponse(String response5) {
                //weatherData.setText("Response is :- ");
                parseData5(response5);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });

        queue5.add(request5);
        super.onStart();



        // Instantiate the String Request for Dharam for front page.
        RequestQueue queue6= Volley.newRequestQueue(getContext());
        String Url6="http://shivagod.in/Prabhu/dharma.php";
        StringRequest request6=new StringRequest(Request.Method.GET, Url6, new Response.Listener<String>() {
            @Override
            public void onResponse(String response6) {
                //weatherData.setText("Response is :- ");
                parseData6(response6);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Network Connectivity Issue", Toast.LENGTH_SHORT).show();

            }
        });

        queue6.add(request6);
        super.onStart();

        // Chat banner open the chatting panel
        chatBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getActivity(),EvaluateMe.class);
                startActivity(intent1);

            }
        });

        return view;

    }

    private void parseData6(String response6) {
        try {
            // Create JSOn Object
            JSONArray jsonArray = new JSONArray(response6);


            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                documentid="dharm_id";
                image1=jsonObject.getString("image");
                Title="The Bhakti";
                description1=jsonObject.getString("timestamp");
                DespTitle=jsonObject.getString("name");
                String MetaData="http://shivagod.in/Prabhu/dharma.php";
                String Product="no_product";

                // Shiva
                Ganesha2 ganesha2=new Ganesha2(DespTitle,description1,documentid,Title,image1,MetaData,Product);
                ganeshaList2.add(ganesha2);


                final RecyclerAdapterGanesha2 recyclerAdapterGanesha2=new RecyclerAdapterGanesha2(getContext(),ganeshaList2);
                ganeshRecView2.setAdapter(recyclerAdapterGanesha2);
                ganeshRecView2.setLayoutManager(new LinearLayoutManager(getActivity()));
                ganeshRecView2.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                ganeshRecView2.setLayoutManager(linearLayoutManager2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void parseData5(String response5) {
        try {
            // Create JSOn Object
            JSONArray jsonArray = new JSONArray(response5);


            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                documentid="shiva_id";
                image1=jsonObject.getString("image");
                Title="The Bhakti";
                description1=jsonObject.getString("timestamp");
                DespTitle=jsonObject.getString("name");
                String MetaData="http://shivagod.in/Prabhu/astrology.php";
                String Product="no_product";

                // Shiva
                Ganesha ganesha=new Ganesha(DespTitle,description1,documentid,Title,image1,MetaData,Product);
                ganeshaList1.add(ganesha);

                final RecyclerAdapterGanesha5 recyclerAdapterGanesha5=new RecyclerAdapterGanesha5(getContext(),ganeshaList1);
                ganesh_recView.setAdapter(recyclerAdapterGanesha5);
                ganesh_recView.setLayoutManager(new LinearLayoutManager(getActivity()));
                ganesh_recView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                ganesh_recView.setLayoutManager(linearLayoutManager);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void parseData2(String response2) {
        try
        {
            JSONObject jsonObject=new JSONObject(response2);
            JSONArray jsonArray=jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                // JSON Objects complete now Collect JSON Array Data which we need
                Video_title2=jsonObject1.getJSONObject("snippet").getString("title");
                Channel_id2=jsonObject1.getJSONObject("snippet").getString("channelId");
                video_id2=jsonObject1.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                playlist_id2=jsonObject1.getJSONObject("snippet").getString("playlistId");
                image_name2=jsonObject1.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                Channel_name2=jsonObject1.getJSONObject("snippet").getString("channelTitle");
                MetaData2 = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLX87es2YBP1u7rQ4uZxtpbhWakGDQFzTC&key=AIzaSyCI1oCTXwZzgVv7LDQ8NykSIUEWt247KnU&maxResults=50";
                //Youtube playlist1
                PL2 pl2= new PL2(Video_title2,Channel_id2,video_id2,playlist_id2,image_name2,MetaData2,Channel_name2);
                pList2.add(pl2);
                RecyclerAdapterPL2 recyclerAdapterPL2 = new RecyclerAdapterPL2(getContext(), pList2);
                list2Rec.setLayoutManager(new LinearLayoutManager(getActivity()));
                list2Rec.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                list2Rec.setLayoutManager(linearLayoutManager);
                list2Rec.setAdapter(recyclerAdapterPL2);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void parseData3(String response3) {
        try
        {
            JSONObject jsonObject=new JSONObject(response3);
            JSONArray jsonArray=jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                // JSON Objects complete now Collect JSON Array Data which we need
                Video_title3=jsonObject1.getJSONObject("snippet").getString("title");
                Channel_id3=jsonObject1.getJSONObject("snippet").getString("channelId");
                video_id3=jsonObject1.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                playlist_id3=jsonObject1.getJSONObject("snippet").getString("playlistId");
                image_name3=jsonObject1.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                Channel_name3=jsonObject1.getJSONObject("snippet").getString("channelTitle");
                MetaData3 = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLX87es2YBP1vGWKUV29ZqQ1ccAGZbI3WN&key=AIzaSyCI1oCTXwZzgVv7LDQ8NykSIUEWt247KnU&maxResults=50";
                //Youtube playlist1
                PL3 pl3 = new PL3(Video_title3,Channel_id3,video_id3,playlist_id3,image_name3,MetaData3,Channel_name3);
                pList3.add(pl3);
                RecyclerAdapterPL3 recyclerAdapterPL3 = new RecyclerAdapterPL3(getContext(), pList3);
                list3Rec.setLayoutManager(new LinearLayoutManager(getActivity()));
                list3Rec.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                list3Rec.setLayoutManager(linearLayoutManager);
                list3Rec.setAdapter(recyclerAdapterPL3);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void parseData4(String response4) {
        try
        {
            JSONObject jsonObject=new JSONObject(response4);
            JSONArray jsonArray=jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                // JSON Objects complete now Collect JSON Array Data which we need
                Video_title1=jsonObject1.getJSONObject("snippet").getString("title");
                Channel_id1=jsonObject1.getJSONObject("snippet").getString("channelId");
                video_id1=jsonObject1.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                playlist_id1=jsonObject1.getJSONObject("snippet").getString("playlistId");
                image_name1=jsonObject1.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                Channel_name1=jsonObject1.getJSONObject("snippet").getString("channelTitle");
                MetaData1 = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLX87es2YBP1sY-KwFkLsB6hCtNy53qgCJ&key=AIzaSyCI1oCTXwZzgVv7LDQ8NykSIUEWt247KnU&maxResults=50";
                //Youtube playlist1
                PL4 pl4 = new PL4(Video_title1,Channel_id1,video_id1,playlist_id1,image_name1,MetaData1,Channel_name1);
                pList4.add(pl4);
                RecyclerAdapterPL4 recyclerAdapterPL4= new RecyclerAdapterPL4(getContext(), pList4);
                list4Rec.setLayoutManager(new LinearLayoutManager(getActivity()));
                list4Rec.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                list4Rec.setLayoutManager(linearLayoutManager);
                list4Rec.setAdapter(recyclerAdapterPL4);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

    }
    // parse youtube list 1
    private void parseData1(String response) {
        try
        {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                // JSON Objects complete now Collect JSON Array Data which we need
                Video_title=jsonObject1.getJSONObject("snippet").getString("title");
                Channel_id=jsonObject1.getJSONObject("snippet").getString("channelId");
                video_id=jsonObject1.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                playlist_id=jsonObject1.getJSONObject("snippet").getString("playlistId");
                image_name=jsonObject1.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                Channel_name=jsonObject1.getJSONObject("snippet").getString("channelTitle");
                MetaData = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLX87es2YBP1u_zNMIH30H3uUFlhhG4eGn&key=AIzaSyCI1oCTXwZzgVv7LDQ8NykSIUEWt247KnU&maxResults=50";
                //Youtube playlist1
                Plist1 playlst = new Plist1(Video_title,Channel_id,video_id,playlist_id,image_name,MetaData,Channel_name);
                plist1.add(playlst);
                RecyclerAdapterPlayLst1 recyclerAdapterPlayLst1= new RecyclerAdapterPlayLst1(getContext(), plist1);
                list1Rec.setLayoutManager(new LinearLayoutManager(getActivity()));
                list1Rec.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                list1Rec.setLayoutManager(linearLayoutManager);
                list1Rec.setAdapter(recyclerAdapterPlayLst1);
            }

        }catch (JSONException e) {
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
        if (context instanceof Tab14.OnFragmentInteractionListener) {
            mListener = (Tab14.OnFragmentInteractionListener) context;
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
