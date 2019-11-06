package com.thebhakti;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Yeshveer on 8/29/2018.
 */

public class RecyclerAdapterPlayLst extends RecyclerView.Adapter<RecyclerAdapterPlayLst.PlaylstViewholder> {
    private Context mcontext;
    private ArrayList<Playlst> playList1;
    public RecyclerAdapterPlayLst(Context mcontext, ArrayList<Playlst> playList1) {
        this.mcontext=mcontext;
        this.playList1=playList1;
    }
    @Override
    public PlaylstViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.playlst_design,parent,false);
        return new PlaylstViewholder(view);
    }

    @Override
    public void onBindViewHolder(PlaylstViewholder holder, final int position) {
        holder.textView.setText(playList1.get(position).getVideo_title());
        Picasso.get().load(playList1.get(position).getImage_name()).into(holder.ImageIcon);
        holder.picLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(mcontext,BhaktiPlayer.class);
                intent1.putExtra("video_title",playList1.get(position).getVideo_title());
                intent1.putExtra("Video_id",playList1.get(position).getVideo_id());
                intent1.putExtra("Playlist_id",playList1.get(position).getPlaylist_id());
                intent1.putExtra("Metadata",playList1.get(position).getMetaData());
                intent1.putExtra("Channel_name",playList1.get(position).getProduct());
                intent1.putExtra("ChannelID",playList1.get(position).getChannel_id());
                intent1.putExtra("url",playList1.get(position).getUrl());
               mcontext.startActivity(intent1);
            }
        });

    }



    @Override
    public int getItemCount() {
        return playList1.size();
    }

    public class PlaylstViewholder extends RecyclerView.ViewHolder {
        private CardView picLay;
        private TextView textView;
        private YouTubeThumbnailView ImageIcon;

        public PlaylstViewholder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.text_view);
            ImageIcon = (YouTubeThumbnailView) itemView.findViewById(R.id.mainImgTab0);
            picLay = (CardView) itemView.findViewById(R.id.picLay);


        }
    }
}
