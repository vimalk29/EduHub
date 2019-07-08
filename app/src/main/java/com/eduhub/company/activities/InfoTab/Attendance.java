package com.eduhub.company.activities.InfoTab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toolbar;

import com.eduhub.company.R;

public class Attendance extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        calendarView = findViewById(R.id.calenderView);
        Attendance.this.setTitle("Attendance");
    }
}
