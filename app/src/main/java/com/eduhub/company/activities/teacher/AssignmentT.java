package com.eduhub.company.activities.teacher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eduhub.company.R;
import com.eduhub.company.adapters.FileSharingAdapter;
import com.eduhub.company.helper.DatabaseManagement;
import com.eduhub.company.model.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class AssignmentT extends AppCompatActivity {

    RecyclerView assignments;
    Button buttonAssignment;
    String TAG = "1234AssT", uploadId;
    DatabaseReference mDatabaseReference;
    ArrayList<Upload> uploads = new ArrayList<>();;
    AVLoadingIndicatorView progressBar;
    final static int PICK_PDF_CODE = 2342;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_t);
        progressBar = findViewById(R.id.avi);
        assignments = findViewById(R.id.uploadAssignments);
        buttonAssignment = findViewById(R.id.buttonAssignmen);
        assignments.setLayoutManager(new LinearLayoutManager(this));
        assignments.setHasFixedSize(true);

        progressBar.show();
        getList();

        buttonAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPDF();
            }
        });

    }

    private void getList(){




        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("teacher").child(Objects.requireNonNull(getSharedPreferences("mypref", MODE_PRIVATE).getString("id", null)))
                .child("data").child("assignment");
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
                FileSharingAdapter fileSharingAdapter = new FileSharingAdapter(uploads,AssignmentT.this);
                assignments.setAdapter(fileSharingAdapter);
                progressBar.hide();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        progressBar.hide();
    }
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(AssignmentT.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }
        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_PDF_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String addressTo, name;
        DatabaseReference mDatabaseReference;
        addressTo = getSharedPreferences("mypref", MODE_PRIVATE).getString("id",null);
        name = "ghj";

        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    name = ""+System.currentTimeMillis();

                new DatabaseManagement(getApplicationContext()).uploadFile(uri,addressTo,name, "assignment");
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
