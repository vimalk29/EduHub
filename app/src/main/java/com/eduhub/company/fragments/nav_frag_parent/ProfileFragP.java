package com.eduhub.company.fragments.nav_frag_parent;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eduhub.company.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragP extends Fragment {

    CircleImageView profilePicture;
    TextView userName,textViewName,textViewEmail,textViewContact,textViewOrg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_frag,null,false);
        profilePicture = view.findViewById(R.id.profilePictureP);
        userName = view.findViewById(R.id.userNameP);
        textViewContact = view.findViewById(R.id.textViewNumberP);
        textViewEmail = view.findViewById(R.id.textViewEmailP);
        textViewName = view.findViewById(R.id.textViewNameP);
        textViewOrg =  view.findViewById(R.id.textViewOrgNameP);

        //firebase and retrive data
        return view;
    }
}
