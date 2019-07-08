package com.eduhub.company.activities.teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.eduhub.company.R;

public class SyllabusT extends AppCompatActivity {

    Button buttonSyllabus;
    RecyclerView uploadSyllabus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_t);

        buttonSyllabus = findViewById(R.id.buttonSyllabus);
        uploadSyllabus = findViewById(R.id.uploadSyllabus);
    }
}
