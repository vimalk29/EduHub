package com.eduhub.company.fragments.nav_frag;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eduhub.company.R;
import com.eduhub.company.adapters.ChatDetailsAdapter;
import com.eduhub.company.helper.DatabaseManagement;
import com.eduhub.company.model.ChatsPOJO;

import java.util.ArrayList;


public class Chat extends Fragment implements View.OnClickListener {
    View myFragment;
    RecyclerView recyclerViewChat;
    FloatingActionButton addNewChat;
    ArrayList<ChatsPOJO> arrayList;
    String senderId;
    DatabaseManagement databaseManagement;
    public Chat(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerViewChat = myFragment.findViewById(R.id.recyclerViewChats);
        addNewChat = myFragment.findViewById(R.id.newChatFloatingButton);
        databaseManagement = new DatabaseManagement(getContext());
        arrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        senderId = sharedPreferences.getString("id", null);
        arrayList = databaseManagement.getChats(senderId);
        ChatDetailsAdapter chatDetailsAdapter = new ChatDetailsAdapter(arrayList, getContext());
        recyclerViewChat.setAdapter(chatDetailsAdapter);
        addNewChat.setOnClickListener(this);
        return myFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newChatFloatingButton :
                //open the all chat recyclerView
                //for now I am working on the same fragment
                addNewChat.hide();
                arrayList = databaseManagement.getAllChats(senderId);
                ChatDetailsAdapter chatDetailsAdapter = new ChatDetailsAdapter(arrayList, getContext());
                recyclerViewChat.setAdapter(chatDetailsAdapter);
                break;
        }
    }
}
