package com.eduhub.company.fragments.nav_frag;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.eduhub.company.helper.DatabaseManagement;
import com.eduhub.company.model.QuestionPOJO;

public class Ask extends Fragment {

    TextInputEditText editTextQues;
    Button  postQues;
    DatabaseManagement databaseManagement;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ask,null,false);
        editTextQues = view.findViewById(R.id.editTextQues);
        postQues = view.findViewById(R.id.postQues);

        postQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionPOJO questionPOJO = new QuestionPOJO();
                String question = editTextQues.getText().toString();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
                String imageUrl =sharedPreferences.getString("profilePic", null);
                String name =sharedPreferences.getString("name",null);
                String userId = sharedPreferences.getString("id",null);
                questionPOJO.setCount(0);
                questionPOJO.setQuestion(question);
                questionPOJO.setImageUrl(imageUrl);
                questionPOJO.setName(name);
                questionPOJO.setUserId(userId);
                questionPOJO.setIs_likes(false);
                questionPOJO.setIs_dis_likes(false);
                databaseManagement = new DatabaseManagement(getContext());
                databaseManagement.uploadQuestion(questionPOJO);
                editTextQues.setText(null);
            }
        });
        return view;
    }
}
