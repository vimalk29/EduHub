package com.eduhub.company.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eduhub.company.R;
import com.eduhub.company.adapters.ChatDetailsAdapter;
import com.eduhub.company.adapters.MessageAdapter;
import com.eduhub.company.helper.DatabaseManagement;
import com.eduhub.company.model.MessagePOJO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoom extends AppCompatActivity {

    CircleImageView imageView;
    TextView textViewName;
    EditText editTextMessage;
    ImageView imageSend;
    RecyclerView recyclerView;
    ArrayList<MessagePOJO> arrayList;
    DatabaseManagement databaseManagement;
    MessageAdapter messageAdapter;
    String TAG = "1234ChatRoom";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseManagement = new DatabaseManagement(getApplicationContext());

        final String senderId = getSharedPreferences("mypref", MODE_PRIVATE).getString("id",null);
        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageView = findViewById(R.id.profileChatRoom);
        textViewName = findViewById(R.id.userNameChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageSend = findViewById(R.id.imageSend);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String imageUrl = intent.getStringExtra("imageUrl");
        final String receiverId = intent.getStringExtra("id");
        Log.d(TAG, "onCreateChatRoom: "+name+" "+imageUrl+" "+receiverId);

        Glide.with(ChatRoom.this).load(imageUrl).into(imageView);
        textViewName.setText(name);

        getMessages(returnChatId(receiverId, senderId));
        imageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextMessage.getText().toString().length() > 0)
                    new DatabaseManagement(getApplicationContext()).sendMessage(receiverId,senderId,editTextMessage.getText().toString());
                editTextMessage.setText(null);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getMessages(String chatId){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("conversation").child("P2P")
                .child(chatId).child("messages");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : ds.getChildren()){
                    MessagePOJO messagePOJO = dataSnapshot.getValue(MessagePOJO.class);
                    arrayList.add(messagePOJO);
                    Log.d(TAG, "gettingMessage: "+messagePOJO.getMessage());
                }
                messageAdapter = new MessageAdapter(arrayList, getApplicationContext());
                recyclerView.setAdapter(messageAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatRoom.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String returnChatId(String senderId, String receiverId){
        if (receiverId.compareTo(senderId) > 0 )
            return receiverId + senderId;
        else
            return senderId + receiverId;
    }

}
