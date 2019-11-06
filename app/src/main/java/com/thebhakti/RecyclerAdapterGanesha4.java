package com.thebhakti;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yeshveer on 8/10/2018.
 */

public class RecyclerAdapterGanesha4 extends RecyclerView.Adapter<RecyclerAdapterGanesha4.GaneshaViewholder> {
    Context mcontext;

    ArrayList<Ganesha> ganeshaList;

    public RecyclerAdapterGanesha4(Context mcontext, List<Ganesha> ganeshaList) {
        this.mcontext = mcontext;
        this.ganeshaList = (ArrayList<Ganesha>) ganeshaList;
    }

    @Override
    public GaneshaViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.item_tab00,parent,false);
        GaneshaViewholder ganeshaViewholder=new GaneshaViewholder(view);

        return ganeshaViewholder;
    }

    @Override
    public void onBindViewHolder(GaneshaViewholder holder,final int position) {
        //To do action with UI
        /*Picasso.get().load(ganeshaList.get(position).getPhoto()).resize(620, 390).into(holder.mainImgTab0);*/
        /*holder.titleUser.setText(ganeshaList.get(position).getTitle());*/
        holder.textView.setText(ganeshaList.get(position).getDespTitle());
        Picasso.get().load(ganeshaList.get(position).getPhoto()).into(holder.ImgLoad);
        //Picasso.with(context).load(dataSet.get(listPosition).getImage()).into(holder.imageEvent);
        holder.cardMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(mcontext,ShareGanesh.class);
                intent1.putExtra("Desc",ganeshaList.get(position).getDesp());
                intent1.putExtra("DescTitle",ganeshaList.get(position).getDespTitle());
                intent1.putExtra("user",ganeshaList.get(position).getTitle());
                intent1.putExtra("DocId",ganeshaList.get(position).getDocId());
                intent1.putExtra("ImgName",ganeshaList.get(position).getPhoto());
                intent1.putExtra("MetaData",ganeshaList.get(position).getMetaData());
                intent1.putExtra("ProdName",ganeshaList.get(position).getProduct());
                mcontext.startActivity(intent1);
            }
        });
    }




    @Override
    public int getItemCount() {
        return ganeshaList.size();
    }

    public class GaneshaViewholder extends RecyclerView.ViewHolder {
        private CardView cardMain;
        private ImageView ImgLoad;
        private TextView textView;

       //Create Constructor for bind UI Widget
        public GaneshaViewholder(View itemView) {
            super(itemView);
            cardMain = (CardView) itemView.findViewById(R.id.cardMain);
            ImgLoad = (ImageView) itemView.findViewById(R.id.ImgLoad);
            textView = (TextView) itemView.findViewById(R.id.textView);
            cardMain = (CardView) itemView.findViewById(R.id.cardMain);


        }
    }
}
