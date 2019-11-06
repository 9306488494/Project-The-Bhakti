package com.thebhakti;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Yeshveer on 8/31/2018.
 */

class RecyclerAdapterPL3 extends RecyclerView.Adapter<RecyclerAdapterPL3.PL3Viewholder> {
    Context mcontext;
    ArrayList<PL3> pList3;
    public RecyclerAdapterPL3(Context mcontext, ArrayList<PL3> pList3) {
        this.mcontext=mcontext;
        this.pList3=pList3;
    }

    @Override
    public PL3Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.common_mixer,parent,false);
        return new PL3Viewholder(view);

    }

    @Override
    public void onBindViewHolder(PL3Viewholder holder, final int position) {
        holder.videoDesc.setText(pList3.get(position).getVideo_title3());
        Picasso.get().load(pList3.get(position).getImage_name3()).into(holder.ImgVideo);
        holder.cardVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(mcontext, BhaktiPlayer.class);
                intent1.putExtra("video_title", pList3.get(position).getVideo_title3());
                intent1.putExtra("Video_id", pList3.get(position).getVideo_id3());
                intent1.putExtra("Playlist_id", pList3.get(position).getPlaylist_id3());
                intent1.putExtra("Metadata", pList3.get(position).getMetaData3());
                intent1.putExtra("Channel_name", pList3.get(position).getChannel_name3());
                intent1.putExtra("ChannelID", pList3.get(position).getChannel_id3());

                mcontext.startActivity(intent1);
            }
        });
    }


    @Override
    public int getItemCount() {
        return pList3.size();
    }

    public class PL3Viewholder extends RecyclerView.ViewHolder {
        private CardView cardVideo;
        private YouTubeThumbnailView ImgVideo;
        private TextView videoDesc;
        public PL3Viewholder(View itemView) {
            super(itemView);
            cardVideo = (CardView) itemView.findViewById(R.id.cardVideo);
            ImgVideo = (YouTubeThumbnailView) itemView.findViewById(R.id.ImgVideo);
            videoDesc = (TextView) itemView.findViewById(R.id.videoDesc);
        }
    }
}
