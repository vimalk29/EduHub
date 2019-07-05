package com.eduhub.company.fragments.nav_frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eduhub.company.R;

import java.util.ArrayList;


public class Home extends Fragment {

    ListView listView;
//    ArrayList<HomePOJO> arrayList = new ArrayList<HomePOJO>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home,null,false);
//        listView = view.findViewById(R.id.listView);

//        HomePOJO homepojo = new HomePOJO();
//
//        HomeAdapter homeAdapter  =  new HomeAdapter(getActivity(), R.layout.home_item,arrayList);
//        listView.setAdapter(homeAdapter);
        return view;
    }
}
