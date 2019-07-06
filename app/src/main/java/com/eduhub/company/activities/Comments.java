package com.eduhub.company.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import com.eduhub.company.R;
import com.eduhub.company.adapters.AnswerAdapter;
import com.eduhub.company.model.AnswerPOJO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Comments extends AppCompatActivity {
    ArrayList<AnswerPOJO> arrayList;
    String questionId;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Intent intent = getIntent();
        questionId = intent.getStringExtra("id");
        recyclerView = findViewById(R.id.recyclerviewComments);
        arrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("question").child(questionId)
                .child("answers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    AnswerPOJO answerPOJO = ds.getValue(AnswerPOJO.class);
                    arrayList.add(answerPOJO);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AnswerAdapter answerAdapter = new AnswerAdapter(arrayList, Comments.this);
        recyclerView.setAdapter(answerAdapter);

    }
}
