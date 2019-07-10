package com.eduhub.company.activities.InfoTab;

import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.eduhub.company.R;
import com.eduhub.company.model.AttendancePOJO;
import com.eduhub.company.model.SetAttendancePOJO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Attendance extends AppCompatActivity {

    TextView startingDate;
    TextView presentDate;
    TextView present;
    TextView absent;
    TextView total;
    int count, totalDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        presentDate =  findViewById(R.id.presentDate);
        present = findViewById(R.id.present);
        absent  = findViewById(R.id.absent);
        total = findViewById(R.id.total);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        presentDate.setText(date);
        final String id = getSharedPreferences("mypref",MODE_PRIVATE).getString("id",null);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("student").child(id).child("teachers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String teacherId = snapshot.getValue(String.class);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("teacher")
                            .child(teacherId).child("data").child("attendance");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            totalDays = (int)dataSnapshot.getChildrenCount();
                            for (DataSnapshot dateS : dataSnapshot.getChildren()){
                                for (DataSnapshot student : dateS.getChildren()){
                                    SetAttendancePOJO data = student.getValue(SetAttendancePOJO.class);
                                    if (data.getStudentIdAtt().equals(id) && data.getIs_presentAtt() == true){
                                        count++;
                                        break;
                                    }
                                }

                            }

                            total.setText(totalDays+"");
                            int x =count/totalDays*100;
                            present.setText( x + " %");
                            absent.setText(100-x + " %");

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
