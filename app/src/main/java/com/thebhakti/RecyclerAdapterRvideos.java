package com.thebhakti;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.AdView;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Yeshveer on 8/30/2018.
 */

class RecyclerAdapterRvideos extends RecyclerView.Adapter<RecyclerAdapterRvideos.RvideosViewholder> {
    Context mcontext;
    ArrayList<RelatedVideos> relatedVideos1;

    public RecyclerAdapterRvideos(Context mcontext, ArrayList<RelatedVideos> relatedVideos1) {
        this.mcontext=mcontext;
        this.relatedVideos1=relatedVideos1;
    }

    @Override
    public RvideosViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.rvideo_lay,parent,false);
        RvideosViewholder rvideosViewholder=new RvideosViewholder(view);

        return rvideosViewholder;
    }

    @Override
    public void onBindViewHolder(RvideosViewholder holder, final int position) {
        Picasso.get().load(relatedVideos1.get(position).getImage_name()).into(holder.ImgVideo);
  /*  holder.ChannelName.setText(relatedVideos1.get(position).getChannel_name());*/
    holder.videoDesc.setText(relatedVideos1.get(position).getVideo_title());
    holder.cardVideo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent1=new Intent(mcontext,BhaktiPlayer.class);

            intent1.putExtra("video_title",relatedVideos1.get(position).getVideo_title());
            intent1.putExtra("Video_id",relatedVideos1.get(position).getVideo_id());
            intent1.putExtra("Playlist_id",relatedVideos1.get(position).getPlaylist_id());
            intent1.putExtra("Metadata",relatedVideos1.get(position).getMetaData());
            intent1.putExtra("Channel_name",relatedVideos1.get(position).getChannel_name());
            intent1.putExtra("ChannelID",relatedVideos1.get(position).getChannel_id());
            intent1.putExtra("url",relatedVideos1.get(position).getUrl());
            mcontext.startActivity(intent1);

        }
    });



    }

    @Override
    public int getItemCount() {
        return relatedVideos1.size();
    }

    public class RvideosViewholder extends RecyclerView.ViewHolder {
        private CardView cardVideo;
        private YouTubeThumbnailView ImgVideo;
        private TextView ChannelName;
        private TextView videoDesc;






        public RvideosViewholder(View itemView) {
            super(itemView);
            cardVideo = (CardView) itemView.findViewById(R.id.cardVideo);
            ImgVideo = (YouTubeThumbnailView) itemView.findViewById(R.id.ImgVideo);
          /*  ChannelName = (TextView) itemView.findViewById(R.id.Channel_name);*/
            videoDesc = (TextView) itemView.findViewById(R.id.videoDesc);
        }
    }
}
