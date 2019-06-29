package com.eduhub.company.fragments.nav_frag;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eduhub.company.R;

public class Ask extends Fragment {

    TextInputEditText editTextQues;
    Button  postQues;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ask,null,false);
        editTextQues = view.findViewById(R.id.editTextQues);
        postQues = view.findViewById(R.id.postQues);

        return view;
    }
}
