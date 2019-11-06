package com.thebhakti;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewUserTab2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewUserTab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewUserTab2 extends Fragment {

    private RecyclerView chatUserRec2;


    ArrayList<ListedUsers>userslist1;
    FirebaseFirestore db;
    CollectionReference cr;
    Query qry;
    String name,id,mobile,colors,state,counter1;
    FirebaseDatabase fd;
    DatabaseReference dr;
    ProgressDialog pd;






    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewUserTab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewUserTab2.
     */
    // TODO: Rename and change types and number of parameters
    public static NewUserTab2 newInstance(String param1, String param2) {
        NewUserTab2 fragment = new NewUserTab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Initilize Array List
        userslist1=new ArrayList<>();


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
        View view=inflater.inflate(R.layout.fragment_new_user_tab2, container, false);
        chatUserRec2 = (RecyclerView) view.findViewById(R.id.chatUserRec2);
        ImageView refresh = (ImageView) view.findViewById(R.id.refresh);

        // progress dialogue
        pd=new ProgressDialog(getContext());
        pd.setTitle("Loading Users...");
        pd.setMessage("Please wait...");
        pd.setCancelable(true);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the list of users for chat
                db=FirebaseFirestore.getInstance();

          /*  //offline storage start
            FirebaseFirestoreSettings settings1 = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();
            db.setFirestoreSettings(settings1);
            // offline storage closed*/


                cr=db.collection("Login_users");
                pd.show();
                qry=cr.whereEqualTo("user_type","chatter");
                qry.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot doc:task.getResult())
                            {

                                if(Objects.equals(doc.getString("user_status"), "seen")) {

                                    colors = doc.getString("user_status");
                                    name = doc.getString("user_name");
                                    mobile = doc.getString("user_mobile");
                                    state = doc.getString("user_email");
                                    counter1 = doc.getString("total_unseen");
                                    id = doc.getId();


                                    ListedUsers listedUsers = new ListedUsers(name, id, mobile, colors,state,counter1);
                                    userslist1.add(listedUsers);
                                    Collections.reverse(userslist1);
                                    RecyclerAdapterUserLists2 recyclerAdapterUserLists2 = new RecyclerAdapterUserLists2(getActivity(), userslist1);
                                    chatUserRec2.setHasFixedSize(true);
                                    chatUserRec2.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    chatUserRec2.setAdapter(recyclerAdapterUserLists2);
                                    pd.dismiss();
                                }


                            }

                        }

                    }

                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "No Network", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        });


            }
        });




        return view;
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
