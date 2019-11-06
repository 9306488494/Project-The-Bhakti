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

class RecyclerAdapterPlayLst1 extends RecyclerView.Adapter<RecyclerAdapterPlayLst1.Playlist1Viewholder> {
    Context mcontext;
    ArrayList<Plist1> plist1;
    public RecyclerAdapterPlayLst1(Context mcontext, ArrayList<Plist1> plist1) {
        this.mcontext=mcontext;
        this.plist1=plist1;
    }

    @Override
    public Playlist1Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.common_mixer,parent,false);

        return new Playlist1Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Playlist1Viewholder holder, final int position) {
        holder.videoDesc.setText(plist1.get(position).getVideo_title());
        Picasso.get().load(plist1.get(position).getImage_name()).into(holder.ImgVideo);
        holder.cardVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(mcontext, BhaktiPlayer.class);
                intent1.putExtra("video_title", plist1.get(position).getVideo_title());
                intent1.putExtra("Video_id", plist1.get(position).getVideo_id());
                intent1.putExtra("Playlist_id", plist1.get(position).getPlaylist_id());
                intent1.putExtra("Metadata", plist1.get(position).getMetaData());
                intent1.putExtra("Channel_name", plist1.get(position).getChannel_name());
                intent1.putExtra("ChannelID", plist1.get(position).getChannel_id());

                mcontext.startActivity(intent1);
            }
        });
    }


        @Override
        public int getItemCount () {
            return plist1.size();
        }

        public class Playlist1Viewholder extends RecyclerView.ViewHolder {

            private CardView cardVideo;
            private YouTubeThumbnailView ImgVideo;
            private TextView videoDesc;


            public Playlist1Viewholder(View itemView) {
                super(itemView);
                cardVideo = (CardView) itemView.findViewById(R.id.cardVideo);
                ImgVideo = (YouTubeThumbnailView) itemView.findViewById(R.id.ImgVideo);
                videoDesc = (TextView) itemView.findViewById(R.id.videoDesc);
            }
        }
    }


