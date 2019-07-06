package com.eduhub.company.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduhub.company.R;
import com.eduhub.company.helper.DatabaseManagement;
import com.eduhub.company.model.MessagePOJO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoom extends AppCompatActivity {

    String name, imageUrl, receiverId;
    CircleImageView imageView;
    TextView textViewName;
    EditText editTextMessage;
    ImageView imageSend;
    RecyclerView recyclerView;
    ArrayList<MessagePOJO> arrayList;
    DatabaseManagement databaseManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseManagement = new DatabaseManagement(getApplicationContext());
        //TODO : Get Senders Id via SharedPreferences
        String senderId = "";
        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageView = findViewById(R.id.imageProfile);
        textViewName = findViewById(R.id.textViewName);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageSend = findViewById(R.id.imageSend);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        imageUrl = intent.getStringExtra("imageUrl");
        receiverId = intent.getStringExtra("id");

        arrayList = databaseManagement.getMessages(receiverId, senderId);



    }
}
