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

class RecyclerAdapterPL4 extends RecyclerView.Adapter<RecyclerAdapterPL4.PL4Viewholder> {
    Context mcontext;
    ArrayList<PL4> pList4;

    public RecyclerAdapterPL4(Context mcontext, ArrayList<PL4> pList4) {
        this.mcontext=mcontext;
        this.pList4=pList4;
    }

    @Override
    public PL4Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.common_mixer,parent,false);
        return new PL4Viewholder(view);
    }

    @Override
    public void onBindViewHolder(PL4Viewholder holder, final int position) {
        holder.videoDesc.setText(pList4.get(position).getVideo_title1());
        Picasso.get().load(pList4.get(position).getImage_name1()).into(holder.ImgVideo);
        holder.cardVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(mcontext, BhaktiPlayer.class);
                intent1.putExtra("video_title", pList4.get(position).getVideo_title1());
                intent1.putExtra("Video_id", pList4.get(position).getVideo_id1());
                intent1.putExtra("Playlist_id", pList4.get(position).getPlaylist_id1());
                intent1.putExtra("Metadata", pList4.get(position).getMetaData1());
                intent1.putExtra("Channel_name", pList4.get(position).getChannel_name1());
                intent1.putExtra("ChannelID", pList4.get(position).getChannel_id1());

                mcontext.startActivity(intent1);
            }
        });
    }


    @Override
    public int getItemCount() {
        return pList4.size();
    }

    public class PL4Viewholder extends RecyclerView.ViewHolder {
        private CardView cardVideo;
        private YouTubeThumbnailView ImgVideo;
        private TextView videoDesc;
        public PL4Viewholder(View itemView) {
            super(itemView);
            cardVideo = (CardView) itemView.findViewById(R.id.cardVideo);
            ImgVideo = (YouTubeThumbnailView) itemView.findViewById(R.id.ImgVideo);
            videoDesc = (TextView) itemView.findViewById(R.id.videoDesc);

        }
    }
}
