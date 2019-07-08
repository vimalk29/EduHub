package com.eduhub.company.activities.teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.eduhub.company.R;

public class AttendanceT extends AppCompatActivity {

    RecyclerView recyclerViewAttendace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_t);

        recyclerViewAttendace = findViewById(R.id.uploadAttendance);

    }
}
