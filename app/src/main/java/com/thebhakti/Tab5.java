package com.thebhakti;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab5.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab5 extends Fragment {

    private CardView Somnath;
    private CardView ujjain;
    private CardView kashi;
    private CardView sidhivinayak;
    private CardView dagdudeth;
    private CardView vrindavan;
    private CardView nainadevi;
    private CardView tuljadevi;
    private CardView vittoba;
    private CardView mahavir;
    private CardView sirdi;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab5() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab5.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab5 newInstance(String param1, String param2) {
        Tab5 fragment = new Tab5();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view= inflater.inflate(R.layout.fragment_tab5, container, false);



        Somnath = (CardView) view.findViewById(R.id.Somnath);
        ujjain = (CardView) view.findViewById(R.id.ujjain);
        kashi = (CardView) view.findViewById(R.id.kashi);
        sidhivinayak = (CardView) view.findViewById(R.id.sidhivinayak);
        dagdudeth = (CardView) view.findViewById(R.id.dagdudeth);
        vrindavan = (CardView) view.findViewById(R.id.vrindavan);
        nainadevi = (CardView) view.findViewById(R.id.nainadevi);
        tuljadevi = (CardView) view.findViewById(R.id.tuljadevi);
        vittoba = (CardView) view.findViewById(R.id.vittoba);
        mahavir = (CardView) view.findViewById(R.id.mahavir);
        sirdi = (CardView) view.findViewById(R.id.sirdi);


        // Send to webview for live sai darshan
        sirdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getContext(),LiveWebview.class);
                intent1.putExtra("title","Live from Sirdi");
                intent1.putExtra("desc","सिरडी के साई बाबा के live दर्शन ");
                intent1.putExtra("url","https://www.sai.org.in/en/sai-video-popup");
                startActivity(intent1);
            }
        });

Somnath.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent1=new Intent(getContext(),LiveWebview.class);
        intent1.putExtra("title","Live from Somnath Mahadev");
        intent1.putExtra("desc","सोमनाथ महादेव Live दर्शन ");
        intent1.putExtra("url","http://shivagod.in/LiveAPI/somnath.html");
        startActivity(intent1);
    }
});
        ujjain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getContext(),LiveWebview.class);
                intent1.putExtra("title","Live from Somnath Mahadev");
                intent1.putExtra("desc","सोमनाथ महादेव Live दर्शन ");
                intent1.putExtra("url","http://shivagod.in/LiveAPI/ujjain.html");
                startActivity(intent1);
            }
        });
        sidhivinayak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getContext(),LiveWebview.class);
                intent1.putExtra("title","Live from Somnath Mahadev");
                intent1.putExtra("desc","सोमनाथ महादेव Live दर्शन ");
                intent1.putExtra("url","http://shivagod.in/LiveAPI/sidhivinayak.html");
                startActivity(intent1);
            }
        });

        kashi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getContext(),LiveWebview.class);
                intent1.putExtra("title","Live from Somnath Mahadev");
                intent1.putExtra("desc","सोमनाथ महादेव Live दर्शन ");
                intent1.putExtra("url","http://shivagod.in/LiveAPI/varansi.html");
                startActivity(intent1);
            }
        });

        dagdudeth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getContext(),LiveWebview.class);
                intent1.putExtra("title","Live from Somnath Mahadev");
                intent1.putExtra("desc","सोमनाथ महादेव Live दर्शन ");
                intent1.putExtra("url","http://shivagod.in/LiveAPI/dagaduseth.html");
                startActivity(intent1);
            }
        });

        vrindavan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getContext(),LiveWebview.class);
                intent1.putExtra("title","Live from Somnath Mahadev");
                intent1.putExtra("desc","सोमनाथ महादेव Live दर्शन ");
                intent1.putExtra("url","http://shivagod.in/LiveAPI/iskcon.html");
                startActivity(intent1);
            }
        });

        nainadevi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getContext(),LiveWebview.class);
                intent1.putExtra("title","Live from Somnath Mahadev");
                intent1.putExtra("desc","सोमनाथ महादेव Live दर्शन ");
                intent1.putExtra("url","http://shivagod.in/LiveAPI/nainadevi.html");
                startActivity(intent1);
            }
        });

        tuljadevi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getContext(),LiveWebview.class);
                intent1.putExtra("title","Live from Somnath Mahadev");
                intent1.putExtra("desc","सोमनाथ महादेव Live दर्शन ");
                intent1.putExtra("url","http://shivagod.in/LiveAPI/tuljabhawani.html");
                startActivity(intent1);
            }
        });

        vittoba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getContext(),LiveWebview.class);
                intent1.putExtra("title","Live from Somnath Mahadev");
                intent1.putExtra("desc","सोमनाथ महादेव Live दर्शन ");
                intent1.putExtra("url","http://shivagod.in/LiveAPI/vitobha.html");
                startActivity(intent1);
            }
        });
        mahavir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getContext(),LiveWebview.class);
                intent1.putExtra("title","Live from Somnath Mahadev");
                intent1.putExtra("desc","सोमनाथ महादेव Live दर्शन ");
                intent1.putExtra("url","http://shivagod.in/LiveAPI/mahavir_patna.html");
                startActivity(intent1);
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
