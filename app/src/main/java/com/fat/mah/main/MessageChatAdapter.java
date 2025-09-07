package com.fat.mah.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fat.mah.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageChatAdapter extends RecyclerView.Adapter<MessageChatAdapter.MyViewHolder> {

    public static final int MESSAGE_TYPE_LEFT=0;
    public static final int MESSAGE_TYPE_RIGHT=1;
    FirebaseUser fuser;

    List<JustChatModel> ls;


    public MessageChatAdapter(List<JustChatModel> ls)
    {
        this.ls=ls;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==MESSAGE_TYPE_RIGHT) {
            View myrow = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
            return new MyViewHolder(myrow);
        }
        else
        {
            View myrow = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
            return new MyViewHolder(myrow);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        JustChatModel chat=ls.get(position);
        boolean isPhoto=chat.getPhotoUrl() != null;
        if(isPhoto) {
            holder.show_message.setVisibility(View.GONE);
            holder.photoImageView.setVisibility(View.VISIBLE);
            //holder.show_message.setText(chat.getText());
           // holder.MsgsUserName.setText(chat.getSender());
            Glide.with(holder.photoImageView.getContext())
                    .load(chat.getPhotoUrl())
                    .into(holder.photoImageView);
        }
        else
        {
            holder.show_message.setVisibility(View.VISIBLE);
            holder.photoImageView.setVisibility(View.GONE);
            holder.show_message.setText(chat.getText());
            //holder.MsgsUserName.setText(chat.getSender());
        }
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {     // view holder
        TextView show_message;
        ImageView photoImageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
           // MsgsUserName = itemView.findViewById(R.id.MsgsUserName);
            photoImageView = itemView.findViewById(R.id.photoImageView);
        }

    }

    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if (ls.get(position).getSender().equals(fuser.getDisplayName()))
        {
            return MESSAGE_TYPE_RIGHT;
        }
        else
        {
            return MESSAGE_TYPE_LEFT;
        }
    }
}
