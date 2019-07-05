package com.eduhub.company.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eduhub.company.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;


public class SignUpFragment extends Fragment {
    private EditText inputEmail, inputPassword,editTextOrgName,editTextAddress,editTextNumber,editTextRePassword;
    private Button btnSignUp;
    private ProgressBar progressBar;
    TextView textViewSignIn;
    private FirebaseAuth auth;
    CircleImageView inputProfile;
//
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
        textViewSignIn = view.findViewById(R.id.textViewSignIn);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextNumber = view.findViewById(R.id.editTextNumber);
        editTextOrgName = view.findViewById(R.id.editTextOrgName);
        editTextRePassword =  view.findViewById(R.id.editTextRePassword);
        inputEmail = view.findViewById(R.id.email);
        inputProfile = view.findViewById(R.id.inputProfile);
        inputPassword = view.findViewById(R.id.password);
        progressBar = view.findViewById(R.id.progressBar);

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

//                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Fragment nextFrag= new UserLoginFragment();
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.activity_first_fragment, nextFrag,"Reset Password Fragment" )
                                                .addToBackStack(null)
                                                .commit();
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
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }

}
