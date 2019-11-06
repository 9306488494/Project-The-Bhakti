package com.thebhakti;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.thebhakti.R.drawable.seen;

/**
 * Created by Yeshveer on 11/17/2018.
 */

class RecyclerAdapterChatMsg extends RecyclerView.Adapter<RecyclerAdapterChatMsg.RelatedChatHolder> {
    private ArrayList<ChatMsg2> msgList2;
    Context mcontext;


    public RecyclerAdapterChatMsg(PhpRegistration mcontext, ArrayList<ChatMsg2> msgList2) {
        this.mcontext=mcontext;
        this.msgList2=msgList2;
    }



    @NonNull
    @Override
    public RelatedChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.chat_rec,parent,false);
        return new RelatedChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedChatHolder holder, int position) {
        // if msg is type of agent then hide other layout
        if(msgList2.get(position).userType.equals("agent"))
        {
            holder.otherLay.setVisibility(View.GONE);
            holder.myText.setText(msgList2.get(position).getMsg());
            holder.txtTime.setText(msgList2.get(position).getTime());
            holder.txtTime.requestFocus();
            // if seen is seen then green otherwise brown
            if(msgList2.get(position).getSeen().equals("seen"))
            {
                holder.tick.setImageResource(R.drawable.seen);
            }
            else
            {
                holder.tick.setImageResource(R.drawable.unseen);
            }

        }
        else
        {
            // if type is admin then use it
            holder.myLay.setVisibility(View.GONE);
            holder.otherText.setText(msgList2.get(position).getMsg());
            holder.txtTimeOther.setText(msgList2.get(position).getTime());
            holder.txtTimeOther.requestFocus();

        }

    }



    @Override
    public int getItemCount() {
        return msgList2.size();
    }

    public class RelatedChatHolder extends RecyclerView.ViewHolder {

        private LinearLayout myTextCard;
        private TextView myText;
        private ImageView rightNobe;
        private TextView txtTime;
        private ImageView tick;
        private ImageView leftNobe;
        private LinearLayout otherTextCard;
        private TextView otherText;
        private ImageView tick2;
        private TextView txtTimeOther;
        private FrameLayout myLay;
        private FrameLayout otherLay;





        public RelatedChatHolder(View itemView) {
            super(itemView);
            myTextCard = (LinearLayout) itemView.findViewById(R.id.myTextCard);
            myText = (TextView) itemView.findViewById(R.id.myText);
            rightNobe = (ImageView) itemView.findViewById(R.id.rightNobe);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            tick = (ImageView) itemView.findViewById(R.id.tick);
            leftNobe = (ImageView) itemView.findViewById(R.id.leftNobe);
            otherTextCard = (LinearLayout) itemView.findViewById(R.id.otherTextCard);
            otherText = (TextView) itemView.findViewById(R.id.otherText);
            tick2 = (ImageView) itemView.findViewById(R.id.tick2);
            txtTimeOther = (TextView) itemView.findViewById(R.id.txtTimeOther);
            myLay = (FrameLayout) itemView.findViewById(R.id.myLay);
            otherLay = (FrameLayout) itemView.findViewById(R.id.otherLay);
        }
    }
}
