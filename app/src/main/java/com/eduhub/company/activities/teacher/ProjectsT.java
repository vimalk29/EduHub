package com.eduhub.company.activities.teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.eduhub.company.R;

public class ProjectsT extends AppCompatActivity {

    Button buttonProject;
    RecyclerView recyclerViewProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_t);

        buttonProject = findViewById(R.id.buttonProjects);
        recyclerViewProjects = findViewById(R.id.recyclerViewProjects);

    }
}
