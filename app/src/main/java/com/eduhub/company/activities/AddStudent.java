package com.eduhub.company.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eduhub.company.R;
import com.eduhub.company.helper.DatabaseManagement;
import com.eduhub.company.helper.SelectImageHelper;
import com.eduhub.company.model.StudentPOJO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddStudent extends AppCompatActivity {

    SelectImageHelper imageHelper;
    CircleImageView profileImage;
    EditText inputStuName,inputStuEmail,inputStuAddress,inputStuParentName,inputStuNumber,inputStuParentNumber;
    Button studentSubmit;
    DatabaseReference databaseReference;
    String id,name,email,number,guardianName,guardianContact,address,profilePicURL;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        inputStuAddress = findViewById(R.id.inputStuAddress);
        inputStuEmail = findViewById(R.id.inputStuEmail);
        inputStuName = findViewById(R.id.inputStuName);
        inputStuNumber = findViewById(R.id.inputStuNumber);
        inputStuParentName = findViewById(R.id.inputStuParentName);
        inputStuParentNumber = findViewById(R.id.inputStuParentNumber);
        studentSubmit = findViewById(R.id.studentSubmit);
        profileImage = findViewById(R.id.inputProfile);
        imageHelper = new SelectImageHelper(this, profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageHelper.selectImageOption();
            }
        });

        studentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = imageHelper.getURI_FOR_SELECTED_IMAGE();
                if (uri == null ){
                    Toast.makeText(AddStudent.this, "Please Select Profile Pic by tapping Icon", Toast.LENGTH_SHORT).show();
                    return;
                }
                 name = inputStuName.getText().toString();
                 email = inputStuEmail.getText().toString();
                 number = inputStuNumber.getText().toString();
                 address = inputStuAddress.getText().toString();
                 guardianContact = inputStuParentNumber.getText().toString();
                 guardianName = inputStuParentName.getText().toString();
                 databaseReference = FirebaseDatabase.getInstance().getReference().child("student");

                if (TextUtils.isEmpty(email) ) {
                    Toast.makeText(AddStudent.this, "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(guardianContact) ) {
                    Toast.makeText(AddStudent.this, "Enter Guardian contact!", Toast.LENGTH_SHORT).show();
                    return;
                }if (TextUtils.isEmpty(address) ) {
                    Toast.makeText(AddStudent.this, "Enter address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name) ) {
                    Toast.makeText(AddStudent.this, "Enter Name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(number)) {
                    Toast.makeText(AddStudent.this, "Enter Number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (number.length() != 10 && guardianContact.length() != 10) {
                    Toast.makeText(AddStudent.this, "Wrong Mobile no.", Toast.LENGTH_SHORT).show();
                    return;
                }

                 final StorageReference storageReference =   FirebaseStorage.getInstance().getReference().child("image/"+inputStuNumber.getText().toString());
                 storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri url) {
                                Log.d("1234", "onSuccess: ");
                                StudentPOJO studentPOJO = new StudentPOJO();
                                String teacherId = getSharedPreferences("mypref", MODE_PRIVATE).getString("id", null);
                                studentPOJO.setAddress(address);
                                studentPOJO.setEmail(email);
                                studentPOJO.setGuardianContact(guardianContact);
                                studentPOJO.setGuardianName(guardianName);
                                studentPOJO.setName(name);
                                studentPOJO.setNumber(number);
                                studentPOJO.setProfilePicURL(url.toString());
                                new DatabaseManagement(getApplicationContext()).createStudent(teacherId,studentPOJO);
                                Toast.makeText(AddStudent.this, "SuccessFully Registered", Toast.LENGTH_SHORT).show();
                                inputStuAddress.setText(null);
                                inputStuEmail.setText(null);
                                inputStuName.setText(null);
                                inputStuParentName.setText(null);
                                inputStuNumber.setText(null);
                                inputStuParentNumber.setText(null);
                                profileImage.setImageResource(R.drawable.ic_account_circle_black_24dp);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                     @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddStudent.this, "StorageError"+e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("1234", "onStorageFailure: "+e.getMessage());
                    }
                });
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imageHelper.handleGrantedPermisson(requestCode,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageHelper.handleResult(requestCode,resultCode,data);
    }
}
