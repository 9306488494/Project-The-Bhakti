package com.thebhakti;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Yeshveer on 8/20/2018.
 */

public class RecyclerAdapterRelated extends RecyclerView.Adapter<RecyclerAdapterRelated.RelatedViewHolder> {
    Context mcontext;
    private ArrayList<DetailsofR> detailRlist1;

    RecyclerAdapterRelated(Context mcontext, ArrayList<DetailsofR> detailRlist1) {
        this.mcontext=mcontext;
        this.detailRlist1=detailRlist1;
    }

    @NonNull
    @Override
    public RelatedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.related_topics,parent,false);
        return new RelatedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.textTitle.setText(detailRlist1.get(position).getTitleR());
        Picasso.get().load(detailRlist1.get(position).getImgR()).into(holder.imgName);


        // click listner for Related Products
        holder.productMainCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(mcontext,ShareGanesh.class);
                intent1.putExtra("Desc",detailRlist1.get(position).getDescR());
                intent1.putExtra("DescTitle",detailRlist1.get(position).getTitleR());
                intent1.putExtra("user",detailRlist1.get(position).getUserR());
                intent1.putExtra("DocId",detailRlist1.get(position).getDocidR());
                intent1.putExtra("ImgName",detailRlist1.get(position).getImgR());
                intent1.putExtra("MetaData",detailRlist1.get(position).getMetaData1());
                intent1.putExtra("ProdName",detailRlist1.get(position).getProdname1());
                mcontext.startActivity(intent1);
            }
        });
    }



    @Override
    public int getItemCount() {
        return detailRlist1.size();
    }

    class RelatedViewHolder extends RecyclerView.ViewHolder {
        private CardView productMainCardview;
        private ImageView imgName;
        private TextView textTitle;

        RelatedViewHolder(View itemView) {
            super(itemView);



            productMainCardview = (CardView) itemView.findViewById(R.id.product_main_cardview);
            imgName = (ImageView) itemView.findViewById(R.id.imgName);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);

        }
    }
}
