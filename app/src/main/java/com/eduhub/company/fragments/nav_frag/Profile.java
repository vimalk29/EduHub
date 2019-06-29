package com.eduhub.company.fragments.nav_frag;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eduhub.company.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {

    CircleImageView profilePicture;
    TextView userName,textViewName,textViewEmail,textViewContact,textViewOrg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,null,false);
        profilePicture = view.findViewById(R.id.profilePicture);
        userName = view.findViewById(R.id.userName);
        textViewContact = view.findViewById(R.id.textViewContact);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewName = view.findViewById(R.id.textViewName);
        textViewOrg =  view.findViewById(R.id.textViewOrg);

        //firebase and retrive data
        return view;
    }
}
