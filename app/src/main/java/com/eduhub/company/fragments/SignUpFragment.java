package com.eduhub.company.fragments;

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
import android.widget.Toast;

import com.eduhub.company.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;


public class SignUpFragment extends Fragment {
    private EditText inputEmail, inputPassword,editTextOrgName,editTextAddress,editTextNumber,editTextRePassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    CircleImageView inputProfile;

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

//        btnSignIn = view.findViewById(R.id.sign_in_button);
        btnSignUp = view.findViewById(R.id.sign_up_button);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextNumber = view.findViewById(R.id.editTextNumber);
        editTextOrgName = view.findViewById(R.id.editTextOrgName);
        editTextRePassword =  view.findViewById(R.id.editTextRePassword);
        inputEmail = view.findViewById(R.id.email);
        inputProfile = view.findViewById(R.id.inputProfile);
        inputPassword = view.findViewById(R.id.password);
        progressBar = view.findViewById(R.id.progressBar);
//        btnResetPassword = view.findViewById(R.id.btn_reset_password);
//
//        btnResetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment nextFrag= new ResetPasswordFragment();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.activity_first_fragment, nextFrag,"Reset Password Fragment" )
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
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

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getActivity(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Fragment nextFrag= new UserLoginFragment();
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.activity_first_fragment, nextFrag,"Reset Password Fragment" )
                                            .addToBackStack(null)
                                            .commit();
                                }
                            }
                        });

            }
        });
        return view;
    }

}
