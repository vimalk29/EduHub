package com.eduhub.company.activities.InfoTab;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.eduhub.company.R;
import com.eduhub.company.adapters.FileSharingAdapter;
import com.eduhub.company.model.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Objects;

public class Marks extends AppCompatActivity {

    RecyclerView assignments;
    Button buttonAssignment;
    String TAG = "1234AssT", uploadId;
    DatabaseReference mDatabaseReference;
    ArrayList<Upload> uploads;
    AVLoadingIndicatorView progressBar;
    final static int PICK_PDF_CODE = 2342;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_t);

        progressBar = findViewById(R.id.avi);
        assignments = findViewById(R.id.uploadAssignments);
        buttonAssignment = findViewById(R.id.buttonAssignmen);
        assignments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        assignments.setHasFixedSize(true);
        progressBar.show();
        getList();
    }
    private void getList(){
        uploads = new ArrayList<>();

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("student")
                .child(Objects.requireNonNull(getSharedPreferences
                        ("mypref", MODE_PRIVATE).getString("id", null))).child("teachers");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String tId = ds.getValue(String.class);
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference()
                            .child("teacher").child(tId)
                            .child("data").child("marks");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            uploads.clear();
                            progressBar.show();
                            for (DataSnapshot uploadedData : dataSnapshot.getChildren()){
                                Upload value = uploadedData.getValue(Upload.class);
                                uploads.add(value);
                                Log.d(TAG, "onDataChangeUploadedFile: "+value.getName());
                            }
                            FileSharingAdapter fileSharingAdapter = new FileSharingAdapter(uploads, getApplicationContext());
                            assignments.setAdapter(fileSharingAdapter);
                            progressBar.hide();
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




        progressBar.hide();
    }
}
