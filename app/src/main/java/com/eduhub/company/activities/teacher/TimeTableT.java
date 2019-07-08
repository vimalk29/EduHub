package com.eduhub.company.activities.teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.eduhub.company.R;

public class TimeTableT extends AppCompatActivity {

    Button buttonTT;
    RecyclerView recyclerViewTT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_t);

        buttonTT = findViewById(R.id.buttonTT);
        recyclerViewTT = findViewById(R.id.uploadTT);

    }
}
