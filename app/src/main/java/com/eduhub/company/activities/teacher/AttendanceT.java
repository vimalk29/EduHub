package com.eduhub.company.activities.teacher;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eduhub.company.R;
import com.eduhub.company.activities.InfoTab.Attendance;
import com.eduhub.company.adapters.AttendanceAdapter;
import com.eduhub.company.fragments.nav_frag_teacher.FragmentUploadTeacher;
import com.eduhub.company.model.AttendancePOJO;
import com.eduhub.company.model.SetAttendancePOJO;
import com.eduhub.company.model.StudentPOJO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AttendanceT extends AppCompatActivity {

    RecyclerView recyclerViewAttendance;
    ArrayList<AttendancePOJO> arrayList  = new ArrayList<>();
    ArrayList<SetAttendancePOJO> atttendanceList = new ArrayList<>();
    DatabaseReference databaseReference;
    String TAG = "1234Attendance";
    AttendanceAdapter attendanceAdapter;
    StudentPOJO studentPOJO;
    AttendancePOJO attendancePOJO;
    SetAttendancePOJO setAttendancePOJO;
    Button buttonSubmit;
    String stuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_t);
        recyclerViewAttendance = findViewById(R.id.uploadAttendance);
        recyclerViewAttendance.setHasFixedSize(true);
        recyclerViewAttendance.setLayoutManager(new LinearLayoutManager(this));
        buttonSubmit = findViewById(R.id.subimtAttendance);

        attendanceAdapter = new AttendanceAdapter(arrayList,AttendanceT.this);
        recyclerViewAttendance.setAdapter(attendanceAdapter);
        getList();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setAttendancePOJO = new SetAttendancePOJO();

                Toast.makeText(AttendanceT.this,"Attendance Submitted",Toast.LENGTH_LONG).show();
                finish();

            }
        });
    }

    public void getList(){
        //arrayList = new ArrayList<>();
        String id = getSharedPreferences("mypref", Context.MODE_PRIVATE).getString("id",null);
//        final AttendanceAdapter attendaneAdapter = new AttendanceAdapter(arrayList,AttendanceT.this);
//        recyclerViewAttendance.setAdapter(attendaneAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("teacher").child(id).child("studentsUnderMe");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String studentId = snapshot.getValue(String.class);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("student").child(studentId)
                            .child("profileInfo");
                    Log.d(TAG, "onDataChange: yha tk aaa gya");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            AttendancePOJO attendancePOJO  = new AttendancePOJO();
                            studentPOJO = dataSnapshot1.getValue(StudentPOJO.class);
                            attendancePOJO.setStudentId(studentPOJO.getId());
                            attendancePOJO.setStudentName(studentPOJO.getName());
                            attendancePOJO.setImgUrl(studentPOJO.getProfilePicURL());
                            attendancePOJO.setIs_present(false);
                            arrayList.add(attendancePOJO);
                            attendanceAdapter.notifyDataSetChanged();
                            Log.d(TAG, "onDataChange: again got it    "+attendancePOJO.getStudentName());
                            for (int i=0;i<arrayList.size();i++){
                                Log.d(TAG, "onDataChangeAttndancePojo: "+i+"   "+arrayList.get(i).getStudentName()+" "
                                        +arrayList.get(i).getStudentId()
                                        +" "+arrayList.get(i).getIs_present());
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                //attendanceAdapter.notifyDataSetChanged();
                Log.d(TAG, "arraylistsize: "+arrayList.size());
                for (int i=0;i<arrayList.size();i++){
                    Log.d(TAG, "onDataChangeAttndancePojo 2: "+i+"   "+arrayList.get(i).getStudentName()+" "
                            +arrayList.get(i).getStudentId()
                            +" "+arrayList.get(i).getIs_present());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public AttendancePOJO getTime(AttendancePOJO attendancePOJO){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return attendancePOJO;
    }
}

