package com.eduhub.company.fragments.nav_frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eduhub.company.R;
import com.eduhub.company.adapters.QuestionAdapter;
import com.eduhub.company.helper.DatabaseManagement;
import com.eduhub.company.model.QuestionPOJO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Home extends Fragment {

    ArrayList<QuestionPOJO> arrayList;
    RecyclerView recyclerView;
    QuestionPOJO questionPOJO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,null,false);
        recyclerView = view.findViewById(R.id.recyclerViewHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        arrayList = new ArrayList<>();
        questionPOJO = new QuestionPOJO();
        //
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("question");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()){
                    questionPOJO = dataSnap.child("questionInfo").getValue(QuestionPOJO.class);
                    arrayList.add(questionPOJO);
                    Log.d("1234", "onDataChange: "+questionPOJO.getQuestion());
                }
                QuestionAdapter questionAdapter = new QuestionAdapter( arrayList ,getContext());
                recyclerView.setAdapter(questionAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //
        return view;
    }
}