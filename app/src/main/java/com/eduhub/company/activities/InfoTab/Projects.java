package com.eduhub.company.activities.InfoTab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.eduhub.company.R;

public class Projects extends AppCompatActivity {

    RecyclerView projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        projects = findViewById(R.id.recyclerViewProjects);

    }
}
