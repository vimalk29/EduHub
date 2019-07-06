package com.eduhub.company.fragments.nav_frag;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eduhub.company.R;
import com.eduhub.company.activities.FirstActivity;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {

    CircleImageView profilePicture;
    TextView userName,textViewName,textViewEmail,textViewContact,textViewOrg;
    String name, number, profileImage, email, organisation;
    Button logout;
    FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,null,false);
        profilePicture = view.findViewById(R.id.profilePicture);
        userName = view.findViewById(R.id.userName);
        textViewContact = view.findViewById(R.id.textViewNumber);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewName = view.findViewById(R.id.textViewName);
        textViewOrg =  view.findViewById(R.id.textViewOrgName);
        logout = view.findViewById(R.id.log_out_button);
        auth = FirebaseAuth.getInstance();
        final SharedPreferences sharedPreferences= getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        name=sharedPreferences.getString("name", null);
        number=sharedPreferences.getString("number", null);
        profileImage=sharedPreferences.getString("profilePic", null);
        email=sharedPreferences.getString("email", null);
        organisation=sharedPreferences.getString("name", null);

        userName.setText(null);
        textViewContact.setText(number);
        textViewEmail.setText(email);
        textViewName.setText(name);
        textViewOrg.setText(organisation);
        Glide.with(getActivity()).load(profileImage).into(profilePicture);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear().apply();
                auth.signOut();
                Intent intent = new Intent(getActivity(), FirstActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
