package com.thebhakti;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Yeshveer on 9/3/2018.
 */

public class RecyclerAdapterUserLists extends RecyclerView.Adapter<RecyclerAdapterUserLists.UserListViewholder> {
    Context mcontext;
    private ArrayList<ListedUsers> userslist;



    RecyclerAdapterUserLists(Context mcontext, ArrayList<ListedUsers> userslist) {
        this.mcontext=mcontext;
        this.userslist=(ArrayList<ListedUsers>) userslist;
    }



    @NonNull
    @Override
    public UserListViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.chatusers4astro_lay,parent,false);
        return new UserListViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserListViewholder holder, final int position) {

                holder.userName.setTextColor(Color.parseColor("#ec3838"));
                holder.userName.setText(userslist.get(position).getName());
                holder.badge.setText(userslist.get(position).getCounter());



              /*  // send unseen notification
                Intent intent1=new Intent(mcontext,AstroChatPanel.class);
                intent1.putExtra("mobile",userslist.get(position).getMobile());
                intent1.putExtra("name",userslist.get(position).getName());
                intent1.putExtra("type","admin");

                PendingIntent pendingIntent = PendingIntent.getActivity(mcontext, 0 , intent1,
                        PendingIntent.FLAG_ONE_SHOT);

                NotificationCompat.Builder notificationbuilder=new NotificationCompat.Builder(mcontext)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Mr./Ms. "+userslist.get(position).getName()+"   send you a message")
                        .setContentText("Need Astrology Solution")
                        .setContentIntent(pendingIntent);
                notificationbuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(mcontext);


                notificationManagerCompat.notify(0,notificationbuilder.build());*/


holder.cardUsers.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent1=new Intent(mcontext,AstroChatPanel.class);
        intent1.putExtra("mobile",userslist.get(position).getMobile());
        intent1.putExtra("name",userslist.get(position).getName());
        intent1.putExtra("type","admin");
        mcontext.startActivity(intent1);
        /*((Activity)mcontext).finish();*/
    }
});

        // Delete Data from realtime database
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase db;
                DatabaseReference dr;
                Task<Void> qry;
                db=FirebaseDatabase.getInstance();
                dr=db.getReference();
                qry=dr.child(userslist.get(position).getMobile()).removeValue();
                Toast.makeText(mcontext, "You can't Remove Live Users", Toast.LENGTH_SHORT).show();
            }
        });// Delete image icon click listner closed

        // Show contact no of the Users below the user name
        holder.txtMobile.setText(userslist.get(position).getMobile());
        holder.txtState.setText(userslist.get(position).getState());
    }




    @Override
    public int getItemCount() {
        return userslist.size();
    }

    class UserListViewholder extends RecyclerView.ViewHolder {
        private CardView cardUsers;
        private TextView userName;
        private ImageView delete;
        private TextView txtMobile;
        private TextView txtState;
        private TextView badge;

        UserListViewholder(View itemView) {
            super(itemView);
            cardUsers = (CardView) itemView.findViewById(R.id.cardUsers);
            userName = (TextView) itemView.findViewById(R.id.userName);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            txtMobile = (TextView) itemView.findViewById(R.id.txtMobile);
            txtState = (TextView) itemView.findViewById(R.id.txtState);
            badge = (TextView) itemView.findViewById(R.id.badge);
        }
    }


}
