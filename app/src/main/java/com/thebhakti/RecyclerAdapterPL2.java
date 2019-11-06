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

class RecyclerAdapterPL2 extends RecyclerView.Adapter<RecyclerAdapterPL2.PL2Viewholder> {
    Context mcontext;
    ArrayList<PL2> pList2;
    public RecyclerAdapterPL2(Context mcontext, ArrayList<PL2> pList2) {
        this.mcontext=mcontext;
        this.pList2=pList2;
    }

    @Override
    public PL2Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.common_mixer,parent,false);
        return new PL2Viewholder(view);
    }

    @Override
    public void onBindViewHolder(PL2Viewholder holder, final int position) {
        holder.videoDesc.setText(pList2.get(position).getVideo_title2());
        Picasso.get().load(pList2.get(position).getImage_name2()).into(holder.ImgVideo);
        holder.cardVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(mcontext, BhaktiPlayer.class);
                intent1.putExtra("video_title", pList2.get(position).getVideo_title2());
                intent1.putExtra("Video_id", pList2.get(position).getVideo_id2());
                intent1.putExtra("Playlist_id", pList2.get(position).getPlaylist_id2());
                intent1.putExtra("Metadata", pList2.get(position).getMetaData2());
                intent1.putExtra("Channel_name", pList2.get(position).getChannel_name2());
                intent1.putExtra("ChannelID", pList2.get(position).getChannel_id2());

                mcontext.startActivity(intent1);
            }
        });
    }



    @Override
    public int getItemCount() {
        return pList2.size();
    }

    public class PL2Viewholder extends RecyclerView.ViewHolder {
        private CardView cardVideo;
        private YouTubeThumbnailView ImgVideo;
        private TextView videoDesc;
        public PL2Viewholder(View itemView) {
            super(itemView);
            cardVideo = (CardView) itemView.findViewById(R.id.cardVideo);
            ImgVideo = (YouTubeThumbnailView) itemView.findViewById(R.id.ImgVideo);
            videoDesc = (TextView) itemView.findViewById(R.id.videoDesc);
        }
    }
}
