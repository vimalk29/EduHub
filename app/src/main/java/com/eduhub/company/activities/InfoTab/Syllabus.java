package com.eduhub.company.activities.InfoTab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.eduhub.company.R;

public class Syllabus extends AppCompatActivity {

    RecyclerView recyclerViewSyllabus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        recyclerViewSyllabus = findViewById(R.id.recyclerViewSyllabus);
    }
}
