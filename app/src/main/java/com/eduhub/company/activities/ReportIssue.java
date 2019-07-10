package com.eduhub.company.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eduhub.company.R;

import org.w3c.dom.Text;

public class ReportIssue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue);
        TextView postQues = findViewById(R.id.postQues);

        postQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "We will look into the Isuue", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
