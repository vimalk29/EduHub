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
    ArrayList<ChatsPOJO> arrayList;
    String senderId;
    DatabaseManagement databaseManagement;
    ChatsPOJO chatsPOJO;
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
        arrayList = new ArrayList<>();
        chatsPOJO = new ChatsPOJO();
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
                if (!allChat){
                    addNewChat.setImageResource(R.drawable.ic_arrow_back_b_24dp);
                    arrayList.clear();
                    //
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("student").child(senderId).child("teachers").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot teacherSnapshot : dataSnapshot.getChildren()){
                                String teacherId = teacherSnapshot.getValue(String.class);
                                Log.d("1234Chat", "teacherId: "+teacherId);
                                FirebaseDatabase.getInstance().getReference().child("teacher").child(teacherId).child("studentsUnderMe").
                                        addValueEventListener(new ValueEventListener() {
                                                                  @Override
                                                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                      for ( DataSnapshot studentSnapshot : dataSnapshot.getChildren()){
                                                                          final String recieverId = studentSnapshot.getValue(String.class);
                                                                          Log.d("1234Chat", "studentId: "+recieverId);

                                                                          //
                                                                          DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("student").child(recieverId).child("profileInfo");
                                                                          databaseReference1.addValueEventListener(new ValueEventListener() {
                                                                              @Override
                                                                              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                  StudentPOJO studentPOJO = dataSnapshot.getValue(StudentPOJO.class);
                                                                                  chatsPOJO.setRecieverId(recieverId);
                                                                                  chatsPOJO.setReceiverPicUrl(studentPOJO.getProfilePicURL());
                                                                                  chatsPOJO.setReceiverName(studentPOJO.getName());
                                                                                  chatsPOJO.setLastMessage("Start a chat, be closer");//LOL
                                                                                  chatsPOJO.setUnseen(false);
                                                                                  arrayList.add(chatsPOJO);
                                                                                  Log.d("1234Chat", "onReturnChatPOJO: "+studentPOJO.getName()+"   "+chatsPOJO.getReceiverName());
                                                                              }
                                                                              @Override
                                                                              public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                                  Log.d(TAG, "onCancelled: "+databaseError.getMessage());
                                                                              }
                                                                          });
                                                                          //
                                                                      }
                                                                  }
                                                                  @Override
                                                                  public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                      Log.d(TAG, "onCancelled: "+databaseError.getMessage());
                                                                  }
                                                              }
                                        );
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: "+databaseError.getMessage());
                        }
                    });
                    //
                    Log.d("1234Chat", "onClick: "+arrayList.get(0).getReceiverName());
                    ChatDetailsAdapter chatDetailsAdapter = new ChatDetailsAdapter(arrayList, getContext());
                    recyclerViewChat.setAdapter(chatDetailsAdapter);
                    allChat = true;
                }
                else{
                    addNewChat.setImageResource(R.drawable.ic_add_white_24dp);
                    arrayList = databaseManagement.getChats(senderId);
                    ChatDetailsAdapter chatDetailsAdapter = new ChatDetailsAdapter(arrayList, getContext());
                    recyclerViewChat.setAdapter(chatDetailsAdapter);
                    allChat = false;
                }
                break;
        }
    }
}