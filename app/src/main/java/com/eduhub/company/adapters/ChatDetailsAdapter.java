package com.eduhub.company.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eduhub.company.R;
import com.eduhub.company.activities.ChatRoom;
import com.eduhub.company.helper.DatabaseManagement;
import com.eduhub.company.model.ChatsPOJO;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatDetailsAdapter extends RecyclerView.Adapter<ChatDetailsAdapter.ViewHolder> {
    Context context;
    ArrayList<ChatsPOJO> arrayList;

    public ChatDetailsAdapter(ArrayList<ChatsPOJO> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }
    @Override
    public ChatDetailsAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.chat_list_item, parent, false);
        ChatDetailsAdapter.ViewHolder viewHolder = new ChatDetailsAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ChatDetailsAdapter.ViewHolder viewHolder, int position) {
        final ChatsPOJO chatsPOJO = arrayList.get(position);
        Log.d("1234ChatDetailAdapter", "onBindViewHolder: "+chatsPOJO.getReceiverName());
        viewHolder.lastMessage.setText(chatsPOJO.getLastMessage());
        viewHolder.name.setText(chatsPOJO.getReceiverName());
        Glide.with(context).load(chatsPOJO.getReceiverPicUrl()).into(viewHolder.profileImage);

        if (chatsPOJO.getUnseen()) {
            viewHolder.unseenSignifier.setVisibility(View.VISIBLE);
            chatsPOJO.setUnseen(false);
        }
        else
            viewHolder.unseenSignifier.setVisibility(View.GONE);

        viewHolder.chatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatRoom.class);
                intent.putExtra("imageUrl",chatsPOJO.getReceiverPicUrl());
                intent.putExtra("name",chatsPOJO.getReceiverName());
                intent.putExtra("id",chatsPOJO.getRecieverId());
                DatabaseManagement databaseManagement = new DatabaseManagement(context);
                String sid = context.getSharedPreferences("mypref", Context.MODE_PRIVATE).getString("id",null);
                FirebaseDatabase.getInstance().getReference()
                        .child("conversation").child("P2P").child(databaseManagement.returnChatId(sid,chatsPOJO.getRecieverId()))
                        .child("unseen").child(sid).setValue(false);

                Log.d("1234ChatDA", "onClick: "+ chatsPOJO.getReceiverName()+chatsPOJO.getReceiverPicUrl()+chatsPOJO.getRecieverId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profileImage;
        TextView name, lastMessage;
        ImageView unseenSignifier;
        RelativeLayout chatLayout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.imageProfile);
            name = itemView.findViewById(R.id.textName);
            lastMessage = itemView.findViewById(R.id.textlastMessage);
            unseenSignifier = itemView.findViewById(R.id.unseenImage);
            chatLayout = itemView.findViewById(R.id.relativeChatLayout);
        }
    }
}