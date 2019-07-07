package com.eduhub.company.fragments.nav_frag;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.eduhub.company.R;
import com.eduhub.company.adapters.ChatDetailsAdapter;
import com.eduhub.company.helper.DatabaseManagement;
import com.eduhub.company.model.ChatsPOJO;
import com.eduhub.company.model.StudentPOJO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Chat extends Fragment implements View.OnClickListener {
    View myFragment;
    RecyclerView recyclerViewChat;
    FloatingActionButton addNewChat;
    private ArrayList<ChatsPOJO> arrayList1;
    String senderId;
    DatabaseManagement databaseManagement;
    ChatDetailsAdapter chatDetailsAdapter;
    //ChatsPOJO chatsPOJO;
    DatabaseReference databaseReference;
    private String TAG = "1234Chat";
    Boolean  allChat = false;
    public Chat(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerViewChat = myFragment.findViewById(R.id.recyclerViewChats);
        recyclerViewChat.setHasFixedSize(true);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(getContext()));
        addNewChat = myFragment.findViewById(R.id.newChatFloatingButton);
        databaseManagement = new DatabaseManagement(getContext());
        arrayList1 = new ArrayList<>();
        //chatsPOJO = new ChatsPOJO();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        senderId = sharedPreferences.getString("id", null);
//        arrayList = databaseManagement.getChats(senderId);
//        chatDetailsAdapter = new ChatDetailsAdapter(arrayList, getContext());
//        recyclerViewChat.setAdapter(chatDetailsAdapter);
        addNewChat.setOnClickListener(this);
        return myFragment;
    }

    int position = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newChatFloatingButton :
                if (!allChat){
                    allChat = true;
                    addNewChat.setImageResource(R.drawable.ic_arrow_back_b_24dp);
                    arrayList1.clear();
                    databaseReference.child("student").child(senderId).child("teachers").addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                        arrayList1.set(position,chatsPOJO);
                                                        position++;
                                                        Log.d(TAG, "onDataChangeChatValue: "+chatsPOJO.getReceiverName());
                                                        chatDetailsAdapter.notifyDataSetChanged();
                                                    }
                                                    for (int i =0;i< arrayList1.size();i++){
                                                        Log.d(TAG, "onDataChangeArray"+i+": "+arrayList1.get(i).getReceiverName());
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
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    chatDetailsAdapter = new ChatDetailsAdapter(arrayList1, getContext());
                    recyclerViewChat.setAdapter(chatDetailsAdapter);

                }
                else{
                    addNewChat.setImageResource(R.drawable.ic_add_white_24dp);
                    arrayList1 = databaseManagement.getChats(senderId);
                    chatDetailsAdapter = new ChatDetailsAdapter(arrayList1, getContext());
                    recyclerViewChat.setAdapter(chatDetailsAdapter);
                    allChat = false;
                }
                break;
        }
    }
}