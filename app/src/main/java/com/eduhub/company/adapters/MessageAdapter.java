package com.eduhub.company.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eduhub.company.R;
import com.eduhub.company.model.MessagePOJO;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    ArrayList<MessagePOJO> arrayList;
    Context context;
    public  MessageAdapter(ArrayList<MessagePOJO> arrayList, Context context){
        this.arrayList = arrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_conversation, parent, false);
        MessageAdapter.ViewHolder viewHolder = new MessageAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {
        MessagePOJO messagePOJO = arrayList.get(i);
        viewHolder.senderTime.setText(messagePOJO.getTime());
        viewHolder.receiverTime.setText(messagePOJO.getTime());
        viewHolder.receiverMesaage.setText(messagePOJO.getMessage());
        viewHolder.senderMessage.setText(messagePOJO.getMessage());

        Glide.with(context).load(messagePOJO.getImageurl()).into(viewHolder.recieverImage);

        String rId = context.getSharedPreferences("mypref",Context.MODE_PRIVATE).getString("id",null);
        Log.d("1234", "onBindViewHolder: "+rId+" sender id :"+messagePOJO.getSenderId());
        if (messagePOJO.getSenderId().equals(rId)){
            Log.d("1234", "onBindViewHolder: SENDER  ");
            viewHolder.rid.setVisibility(View.GONE);
            viewHolder.sid.setVisibility(View.VISIBLE);
        }else{
            Log.d("1234", "onBindViewHolder: receiver");
            viewHolder.sid.setVisibility(View.GONE);
            viewHolder.rid.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView recieverImage;
        TextView senderMessage, receiverMesaage, senderTime, receiverTime;
        LinearLayout rid;
        RelativeLayout sid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rid=itemView.findViewById(R.id.rid);
            sid = itemView.findViewById(R.id.sid);
            recieverImage = itemView.findViewById(R.id.mesProfile);
            senderMessage = itemView.findViewById(R.id.messageSender);
            receiverMesaage = itemView.findViewById(R.id.messageReceiver);
            receiverTime =itemView.findViewById(R.id.messageTimeR);
            senderTime = itemView.findViewById(R.id.messageTimes);
        }
    }
}
