package com.eduhub.company.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.eduhub.company.activities.MainActivityStudent;
import com.eduhub.company.activities.parent.MainActivityParent;
import com.eduhub.company.activities.teacher.MainActivityT;
import com.eduhub.company.model.StudentPOJO;
import com.eduhub.company.model.TeacherPOJO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

public class UserLoginFragment extends Fragment {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth mAuth;
    Button btnSignUp, btnLogin;
    TextView textViewResetPassword;
    String studentId;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    AVLoadingIndicatorView progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_user_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        progressBar = view.findViewById(R.id.avi);
        inputEmail = view.findViewById(R.id.email);
        inputPassword = view.findViewById(R.id.password);
        progressBar = view.findViewById(R.id.progressBar);
        btnSignUp = view.findViewById(R.id.btn_signup);
        btnLogin = view.findViewById(R.id.btn_login);
        textViewResetPassword = view.findViewById(R.id.reset_password);

        inputEmail.setText("vimalkumawat99@gmail.com");
        inputPassword.setText("adminvim");

        textViewResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nextFrag = new ResetPasswordFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_first_fragment, nextFrag, "Reset Password Fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nextFrag = new SignUpFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_first_fragment, nextFrag, "Sign Up Fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.show();
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if (mAuth.getCurrentUser().isEmailVerified()) {

                                        //TODO put everything in database and in shared preferences
                                        final String userId = mAuth.getUid();
                                        databaseReference = FirebaseDatabase.getInstance().getReference("teacher");
                                        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                TeacherPOJO teacherPOJO = new TeacherPOJO();
                                                teacherPOJO = dataSnapshot.child("profileInfo").getValue(TeacherPOJO.class);
                                                SharedPreferences sharedPreferences = getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("id", userId);
                                                editor.putString("name", teacherPOJO.getName());
                                                editor.putString("email", teacherPOJO.getEmail());
                                                editor.putString("address", teacherPOJO.getAddress());
                                                editor.putString("profilePic", teacherPOJO.getProfilePicURL());
                                                editor.putString("number", teacherPOJO.getNumber());
                                                editor.putString("type", "T");
                                                editor.apply();
                                                progressBar.hide();
                                                Intent intent = new Intent(getActivity(), MainActivityT.class);
                                                startActivity(intent);
                                                progressBar.hide();
                                                getActivity().finish();
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) { }
                                        });
                                    }
                                    else{
                                        Toast.makeText(getActivity(), "Not Verified", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    //Toast.makeText(getActivity(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    databaseReference = FirebaseDatabase.getInstance().getReference("student");
                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot dataSnaps : dataSnapshot.getChildren()){
                                                String number = dataSnaps.child("profileInfo").child("number").getValue(String.class);
                                                String password = dataSnaps.child("profileInfo").child("password").getValue(String.class);
                                                if (number.equals(inputEmail.getText().toString()) && password.equals(inputPassword.getText().toString())){
                                                    StudentPOJO studentPOJO = dataSnaps.child("profileInfo").getValue(StudentPOJO.class);
                                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("id", studentPOJO.getId());
                                                    editor.putString("name", studentPOJO.getName());
                                                    editor.putString("email", studentPOJO.getEmail());
                                                    editor.putString("address", studentPOJO.getAddress());
                                                    editor.putString("profilePic", studentPOJO.getProfilePicURL());
                                                    editor.putString("number", studentPOJO.getNumber());
                                                    editor.putString("type", "S");
                                                    editor.apply();

                                                    Intent intent = new Intent(getActivity(), MainActivityStudent.class);
                                                    startActivity(intent);
                                                    progressBar.hide();
                                                    getActivity().finish();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    databaseReference = FirebaseDatabase.getInstance().getReference("parent");
                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (final DataSnapshot dataSnaps : dataSnapshot.getChildren()){
                                                String id = "";
                                                if(dataSnaps.getKey() != null){
                                                    id = dataSnaps.getKey();
                                                }
                                                String number = dataSnaps.child("username").getValue(String.class);
                                                String password = dataSnaps.child("password").getValue(String.class);

                                                if (number.equals(inputEmail.getText().toString()) && password.equals(inputPassword.getText().toString())){

                                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("parent")
                                                            .child(id);
                                                    Log.d("1234", "Value of parent Id "+id);

                                                    db.child("ward").addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    for (DataSnapshot wardSnaps : dataSnapshot.getChildren()){
                                                                        studentId = wardSnaps.getValue(String.class);
                                                                        Log.d("1234", "Value of student Id "+studentId);
                                                                        DatabaseReference ds = FirebaseDatabase.getInstance()
                                                                                .getReference("student").child(studentId);
                                                                        ds.child("profileInfo").addValueEventListener(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                StudentPOJO studentPOJO = dataSnapshot.getValue(StudentPOJO.class);
                                                                                SharedPreferences sharedPreferences = getContext()
                                                                                        .getSharedPreferences("mypref", Context.MODE_PRIVATE);
                                                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                                editor.putString("email", studentPOJO.getEmail());
                                                                                editor.putString("id", studentPOJO.getId());
                                                                                editor.putString("name", studentPOJO.getGuardianName());
                                                                                editor.putString("address", studentPOJO.getAddress());
                                                                                editor.putString("profilePic", studentPOJO.getProfilePicURL());
                                                                                editor.putString("number", studentPOJO.getGuardianContact());
                                                                                editor.putString("type", "P");
                                                                                editor.apply();
                                                                                Intent intent = new Intent(getActivity(), MainActivityParent.class);
                                                                                startActivity(intent);
                                                                                progressBar.hide();
                                                                                getActivity().finish();
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

                                                }
                                            }
//                                            Toast.makeText(getContext(), "Error Logging In", Toast.LENGTH_SHORT).show();
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }
}
