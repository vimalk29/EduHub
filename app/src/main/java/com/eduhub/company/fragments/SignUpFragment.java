package com.eduhub.company.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eduhub.company.R;
import com.eduhub.company.activities.FirstActivity;
import com.eduhub.company.helper.DatabaseManagement;
import com.eduhub.company.helper.SelectImageHelper;
import com.eduhub.company.model.TeacherPOJO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class SignUpFragment extends Fragment {
    private EditText inputEmail, inputPassword,editTextOrgName,editTextAddress,editTextNumber,editTextRePassword;
    private Button btnSignUp;
    private ProgressBar progressBar;
    TextView textViewSignIn;
    private FirebaseAuth auth;
    Uri uri;
    CircleImageView inputProfile;
    SelectImageHelper imageHelper;
    DatabaseManagement databaseManagement;

//    @Override
//    public void onResume() {
//        super.onResume();
//        progressBar.setVisibility(View.GONE);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        btnSignUp = view.findViewById(R.id.sign_up_button);
        textViewSignIn = view.findViewById(R.id.text_Sign);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextNumber = view.findViewById(R.id.editTextNumber);
        editTextOrgName = view.findViewById(R.id.editTextOrgName);
        editTextRePassword =  view.findViewById(R.id.editTextRePassword);
        inputEmail = view.findViewById(R.id.email);
        inputProfile = view.findViewById(R.id.inputProfile);
        inputPassword = view.findViewById(R.id.password);
        progressBar = view.findViewById(R.id.progressBar);
        imageHelper = new SelectImageHelper(getActivity(), inputProfile);

        /////////////////////////////////////
        inputEmail.setText("vimalkumawat99@gmail.com");
        inputPassword.setText("adminvim");
        editTextRePassword.setText("adminvim");
        editTextAddress.setText("jaipur");
        editTextNumber.setText("9694540876");
        editTextOrgName.setText("EDUHUB");


        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nextFrag= new UserLoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_first_fragment, nextFrag,"Reset Password Fragment" )
                        .addToBackStack(null)
                        .commit();
            }
        });
        databaseManagement = new DatabaseManagement(getActivity());
        inputProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageHelper.selectImageOption();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (imageHelper.getURI_FOR_SELECTED_IMAGE() == null){
                    Toast.makeText(getContext(), "Please Select Profile Pic by tapping Icon", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        uri = imageHelper.getURI_FOR_SELECTED_IMAGE();
                                        final StorageReference storageReference =   FirebaseStorage.getInstance().getReference().child("image/"+editTextNumber.getText().toString());
                                        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(final Uri url) {
                                                        Log.d("1234", "onSuccess: ");
                                                        TeacherPOJO teacherPOJO = new TeacherPOJO();
                                                        teacherPOJO.setAddress(editTextAddress.getText().toString());
                                                        teacherPOJO.setEmail(auth.getCurrentUser().getEmail());
                                                        teacherPOJO.setName(editTextOrgName.getText().toString());
                                                        teacherPOJO.setNumber(editTextNumber.getText().toString());
                                                        teacherPOJO.setProfilePicURL(url.toString());
                                                        teacherPOJO.setTeacherId(auth.getUid());
                                                        databaseManagement.createTeacher(auth.getUid(), teacherPOJO);
                                                        Toast.makeText(getActivity(), "SuccessFully Registered", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "StorageError"+e.getMessage(), Toast.LENGTH_LONG).show();
                                                Log.d("1234", "onStorageFailure: "+e.getMessage());
                                            }
                                        });
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Fragment nextFrag= new UserLoginFragment();
                                                getActivity().getSupportFragmentManager().beginTransaction()
                                                        .replace(R.id.activity_first_fragment, nextFrag,"Reset Password Fragment" )
                                                        .addToBackStack(null)
                                                        .commit();
                                            }
                                        }, 3500);
                                    }
                                    else {
                                        Toast.makeText(getActivity(),""+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(getActivity(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "signupFailed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("1234", "onFailure: "+e.getMessage());
                    }
                });
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageHelper.handleResult(requestCode, resultCode, data);  // call this helper class method
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imageHelper.handleGrantedPermisson(requestCode, grantResults);   // call this helper class method
    }
}