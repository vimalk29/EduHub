package com.eduhub.company.activities.teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.eduhub.company.R;

public class MarksT extends AppCompatActivity {

    Button buttonMarks;
    RecyclerView recyclerViewMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_t);

        buttonMarks = findViewById(R.id.buttonMarks);
        recyclerViewMarks = findViewById(R.id.uploadMarks);

    }
}
