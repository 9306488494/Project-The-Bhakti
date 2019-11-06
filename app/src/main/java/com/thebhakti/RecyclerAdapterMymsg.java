package com.thebhakti;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Yeshveer on 9/3/2018.
 */

class RecyclerAdapterMymsg extends RecyclerView.Adapter<RecyclerAdapterMymsg.MymsgViewholder> {
    Context mcontext;
    ArrayList<Mymsg> msglist;

    public RecyclerAdapterMymsg(Context mcontext, ArrayList<Mymsg> msglist) {
        this.mcontext=mcontext;
        this.msglist=(ArrayList<Mymsg>) msglist;
    }

    @NonNull
    @Override
    public MymsgViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.userchat_lay,parent,false);
        return new MymsgViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MymsgViewholder holder, int position) {
        if(msglist.get(position).getFetch_msg().equals(""))
        {
            holder.myText.setVisibility(View.GONE);
            holder.myTextCard.setVisibility(View.GONE);
            holder.otherText.setText(msglist.get(position).getBabamsg());
            holder.otherTextCard.requestFocus();
            holder.rightNobe.setVisibility(View.GONE);


        }
        else
            {
                holder.otherText.setVisibility(View.GONE);
                holder.otherTextCard.setVisibility(View.GONE);
                holder.myText.setText(msglist.get(position).getFetch_msg());
                holder.myTextCard.requestFocus();
                holder.leftNobe.setVisibility(View.GONE);

        }
        if(msglist.get(position).getCheckSeen().equals("seen"))
        {
            holder.tick.setImageResource(R.drawable.seen);
            holder.tick2.setImageResource(R.drawable.seen);
            holder.txtTime.setText(msglist.get(position).getTime());
            holder.txtTimeOther.setText(msglist.get(position).getTime());


        }
        else{
            holder.tick.setImageResource(R.drawable.unseen);
            holder.tick2.setImageResource(R.drawable.unseen);
            holder.txtTime.setText(msglist.get(position).getTime());
            holder.txtTimeOther.setText(msglist.get(position).getTime());
        }


    }



    @Override
    public int getItemCount() {
        return msglist.size();
    }

    public class MymsgViewholder extends RecyclerView.ViewHolder {
        private TextView myText;
        private TextView otherText;
        private LinearLayout myTextCard;
        private LinearLayout otherTextCard;
        private ImageView tick;
        private ImageView tick2;
        private TextView txtTime;
        private TextView txtTimeOther;
        private ImageView rightNobe;
        private ImageView leftNobe;


        public MymsgViewholder(View itemView) {
            super(itemView);
            myText = (TextView) itemView.findViewById(R.id.myText);
            otherText = (TextView) itemView.findViewById(R.id.otherText);
            otherText = (TextView) itemView.findViewById(R.id.otherText);
            myTextCard = (LinearLayout) itemView.findViewById(R.id.myTextCard);
            otherTextCard = (LinearLayout) itemView.findViewById(R.id.otherTextCard);
            tick = (ImageView) itemView.findViewById(R.id.tick);
            tick2 = (ImageView) itemView.findViewById(R.id.tick2);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtTimeOther = (TextView) itemView.findViewById(R.id.txtTimeOther);
            rightNobe = (ImageView) itemView.findViewById(R.id.rightNobe);
            leftNobe = (ImageView) itemView.findViewById(R.id.leftNobe);


        }
    }
}
