package com.eduhub.company.fragments.nav_frag_parent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduhub.company.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ChatFragP extends Fragment {

    String studentId, teacherId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.call_frag, null, false);
        RelativeLayout contactMeLayout = view.findViewById(R.id.contactMeLayout);
        final TextView textView = view.findViewById(R.id.contactMeNo);
        textView.setText("Loading......");


        studentId = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE).getString("id",null);
                FirebaseDatabase.getInstance().getReference().child("student").child(studentId).child("teachers")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds1: dataSnapshot.getChildren()){
                                    teacherId = ds1.getValue(String.class);
                                    FirebaseDatabase.getInstance().getReference().child("teacher")
                                            .child(teacherId).child("profileInfo").child("number").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            textView.setText(dataSnapshot.getValue(String.class));
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(getContext(), "Error Loading Contact", Toast.LENGTH_SHORT).show();
                                            textView.setText("error!!!");
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "Error Loading Contact", Toast.LENGTH_SHORT).show();
                                textView.setText("error!!!");

                            }
                        });

        contactMeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = textView.getText().toString();
                if (number.length()!=0){
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+number));
                    startActivity(callIntent);
                }
                else
                    Toast.makeText(getContext(), "Error Calling", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
