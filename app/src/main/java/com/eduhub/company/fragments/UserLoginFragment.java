package com.eduhub.company.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import com.eduhub.company.activities.Dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLoginFragment extends Fragment {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    Button btnSignUp, btnLogin;
    TextView textViewResetPassword;
    FirebaseAuth auth;

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

        inputEmail = view.findViewById(R.id.email);
        inputPassword = view.findViewById(R.id.password);
        progressBar = view.findViewById(R.id.progressBar);
        btnSignUp = view.findViewById(R.id.btn_signup);
        btnLogin = view.findViewById(R.id.btn_login);
        textViewResetPassword = view.findViewById(R.id.textViewResetPassword);

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
                progressBar.setVisibility(View.VISIBLE);
                //authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
//                                if (!task.isSuccessful()) {
//                                    // there was an error
//                                    if (password.length() < 6) {
//                                        inputPassword.setError(getString(R.string.minimum_password));
//                                    } else {
//                                        Snackbar.make(getActivity().findViewById(android.R.id.content),
//                                                task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
//                                    }
//                                } else {
////                                    Intent intent = new Intent(getActivity(), MainActivity.class);
////                                    startActivity(intent);
//
//                                    Toast.makeText(getActivity(), "LoggedIn", Toast.LENGTH_LONG).show();
//                                }

                                if (task.isSuccessful()) {

                                    if (auth.getCurrentUser().isEmailVerified()) {

                                        Intent intent = new Intent(getActivity(), Dashboard.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        Toast.makeText(getActivity(), "Not Verified", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(getActivity(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
