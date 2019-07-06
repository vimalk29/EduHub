package com.eduhub.company.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.eduhub.company.R;

public class AssignmentT extends AppCompatActivity {

    ListView listViewAssignment;
    Button buttonAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_t);

        listViewAssignment = findViewById(R.id.listViewAssignment);
        buttonAssignment = findViewById(R.id.buttonAssignment);

    }
}
