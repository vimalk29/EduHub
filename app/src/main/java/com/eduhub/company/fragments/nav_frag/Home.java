package com.eduhub.company.fragments.nav_frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eduhub.company.R;
import com.eduhub.company.adapters.QuestionAdapter;
import com.eduhub.company.helper.DatabaseManagement;

import java.util.ArrayList;


public class Home extends Fragment {

    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,null,false);
        recyclerView = view.findViewById(R.id.recyclerViewHome);
        QuestionAdapter questionAdapter = new QuestionAdapter( new DatabaseManagement(getContext()).getQuestion(),getContext());
        recyclerView.setAdapter(questionAdapter);
        return view;
    }
}