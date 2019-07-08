package com.eduhub.company.fragments.nav_frag;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eduhub.company.R;
import com.eduhub.company.adapters.ChatDetailsAdapter;
import com.eduhub.company.helper.DatabaseManagement;
import com.eduhub.company.model.ChatsPOJO;
import com.eduhub.company.model.MessagePOJO;
import com.eduhub.company.model.StudentPOJO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Chat extends Fragment implements View.OnClickListener {
    View myFragment;
    ChatsPOJO chatsPOJO;
    RecyclerView recyclerViewChat, recyclerViewContact;
    FloatingActionButton addNewChat;
    private ArrayList<ChatsPOJO> arrayList1, arrayListChat;
    String senderId;
    DatabaseManagement databaseManagement;
    ChatDetailsAdapter chatDetailsAdapterChat, chatDetailsAdapterContact;
    Button chatButton, contactButton;
    DatabaseReference databaseReference;
    private String TAG = "1234Chat";
    Boolean  allChat = false;
    public Chat(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerViewChat = myFragment.findViewById(R.id.recyclerViewChats);
        recyclerViewContact = myFragment.findViewById(R.id.recyclerViewContacts);
        recyclerViewChat.setHasFixedSize(true);
        recyclerViewContact.setHasFixedSize(true);
        recyclerViewContact.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(getContext()));
        addNewChat = myFragment.findViewById(R.id.newChatFloatingButton);
        databaseManagement = new DatabaseManagement(getContext());
        arrayList1 = new ArrayList<>();
        chatButton = myFragment.findViewById(R.id.chat);
        contactButton = myFragment.findViewById(R.id.allChat);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        senderId = sharedPreferences.getString("id", null);

        addNewChat.setOnClickListener(this);
        chatButton.setOnClickListener(this);
        contactButton.setOnClickListener(this);

        getChats();
        chatDetailsAdapterChat = new ChatDetailsAdapter(arrayListChat, getContext());
        recyclerViewChat.setAdapter(chatDetailsAdapterChat);

        recyclerViewContact.setVisibility(View.GONE);
        recyclerViewChat.setVisibility(View.VISIBLE);

        getContact();
        chatDetailsAdapterContact = new ChatDetailsAdapter(arrayList1, getContext());
        recyclerViewContact.setAdapter(chatDetailsAdapterContact);

        return myFragment;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newChatFloatingButton :
                if (!allChat){
                    recyclerViewChat.setVisibility(View.GONE);
                    recyclerViewContact.setVisibility(View.VISIBLE);
                    addNewChat.setImageResource(R.drawable.ic_arrow_back_b_24dp);

                    allChat = true;

                }
                else{
                    recyclerViewContact.setVisibility(View.GONE);
                    recyclerViewChat.setVisibility(View.VISIBLE);
                    addNewChat.setImageResource(R.drawable.ic_add_white_24dp);
                    allChat = false;
                }
                break;
            case R.id.chat:
                recyclerViewContact.setVisibility(View.GONE);
                recyclerViewChat.setVisibility(View.VISIBLE);
                addNewChat.setImageResource(R.drawable.ic_add_white_24dp);
                allChat = true;
                break;
            case R.id.allChat:
                recyclerViewChat.setVisibility(View.GONE);
                recyclerViewContact.setVisibility(View.VISIBLE);
                addNewChat.setImageResource(R.drawable.ic_arrow_back_b_24dp);
                allChat = false;
                break;
        }
    }
    private void getContact() {
        arrayList1 = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("student").child(senderId)
                .child("teachers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String teacherId = ds.getValue(String.class);
                    DatabaseReference dbReference = databaseReference;
                    dbReference.child("teacher").child(teacherId).child("studentsUnderMe").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshotTeacher) {
                            for (DataSnapshot ds1 : dataSnapshotTeacher.getChildren()){
                                String studentId = ds1.getValue(String.class);
                                DatabaseReference drf = databaseReference.child("student").child(studentId).child("profileInfo");
                                Log.d(TAG, "onDataChangeStudent: "+studentId);
                                drf.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshotStu) {
                                        StudentPOJO studentPOJO = dataSnapshotStu.getValue(StudentPOJO.class);
                                        if (!senderId.equals(studentPOJO.getId())) {
                                            ChatsPOJO chatsPOJO = new ChatsPOJO();
                                            chatsPOJO.setUnseen(false);
                                            chatsPOJO.setLastMessage("Start a convo, Be closer");
                                            chatsPOJO.setReceiverName(studentPOJO.getName());
                                            chatsPOJO.setRecieverId(studentPOJO.getId());
                                            chatsPOJO.setReceiverPicUrl(studentPOJO.getProfilePicURL());
                                            arrayList1.add(chatsPOJO);
//                                                        arrayList1.set(position,chatsPOJO);
//                                                        position++;
                                            Log.d(TAG, "onDataChangeChatValue: "+chatsPOJO.getReceiverName());

                                        }
                                        for (int i =0;i< arrayList1.size();i++){
                                            Log.d(TAG, "onDataChangeArray"+i+": "+arrayList1.get(i).getReceiverName());
                                        }chatDetailsAdapterContact.notifyDataSetChanged();
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getChats(){
        arrayListChat = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("student").child(getContext()
                .getSharedPreferences("mypref", Context.MODE_PRIVATE)
                .getString("id", null)).child("contacts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayListChat.clear();
                for (DataSnapshot dataSnaps : dataSnapshot.getChildren()){
                    String receiverId = dataSnaps.getValue(String.class);
                    final String chatId = databaseManagement.returnChatId(senderId,receiverId);
                    chatsPOJO = new ChatsPOJO();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("conversation")
                            .child("P2P").child(chatId);
                    reference.child("lastMessage")
                            .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            MessagePOJO messagePOJO = dataSnapshot.getValue(MessagePOJO.class);
                            chatsPOJO.setLastMessage(messagePOJO.getMessage());
                            chatDetailsAdapterChat.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
                    reference.child("unseen").child(senderId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Boolean unseen = dataSnapshot.getValue(Boolean.class);
                            chatsPOJO.setUnseen(unseen);
                            chatDetailsAdapterChat.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    chatsPOJO.setRecieverId(receiverId);
                    reference = FirebaseDatabase.getInstance().getReference("student").child(receiverId);
                    reference.child("profileInfo").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            StudentPOJO studentPOJO = dataSnapshot.getValue(StudentPOJO.class);
                            String picUrl = studentPOJO.getProfilePicURL();
                            String name = studentPOJO.getName();
                            chatsPOJO.setReceiverName(name);
                            chatsPOJO.setReceiverPicUrl(picUrl);
                            arrayListChat.add(chatsPOJO);
                            Log.d(TAG, "Array List Of Chats Users are: "+ chatsPOJO.getReceiverName());
                            chatDetailsAdapterChat.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}