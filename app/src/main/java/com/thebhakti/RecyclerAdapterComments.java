package com.thebhakti;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Yeshveer on 9/26/2018.
 */

class RecyclerAdapterComments extends RecyclerView.Adapter<RecyclerAdapterComments.CommentsViewholder>{
    Context mcontext;
    private ArrayList<Comments> comments1;
    RecyclerAdapterComments(Context mcontext, ArrayList<Comments> comments1) {
        this.mcontext=mcontext;
        this.comments1=comments1;

    }

    @NonNull
    @Override
    public CommentsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(mcontext).inflate(R.layout.comments_lay,parent,false);
        return new CommentsViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterComments.CommentsViewholder holder, int position) {
        holder.commentor.setText(comments1.get(position).getName());
        holder.loadCTxt.setText(comments1.get(position).getComment());


    }



    @Override
    public int getItemCount() {
        return comments1.size();
    }

    public class CommentsViewholder extends RecyclerView.ViewHolder {

        private CardView cmtCard;
        private TextView loadCTxt;
        private TextView commentor;





        public CommentsViewholder(View itemView) {
            super(itemView);
            cmtCard = (CardView) itemView.findViewById(R.id.cmtCard);
            loadCTxt = (TextView) itemView.findViewById(R.id.loadCTxt);
            commentor = (TextView) itemView.findViewById(R.id.commentor);
        }
    }
}
