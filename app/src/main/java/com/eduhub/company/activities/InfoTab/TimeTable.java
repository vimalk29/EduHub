package com.eduhub.company.activities.InfoTab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.eduhub.company.R;

public class TimeTable extends AppCompatActivity {

    RecyclerView recyclerViewTT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        recyclerViewTT = findViewById(R.id.recyclerViewTT);

    }
}
