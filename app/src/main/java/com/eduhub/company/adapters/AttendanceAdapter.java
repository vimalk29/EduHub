package com.eduhub.company.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eduhub.company.R;
import com.eduhub.company.model.AttendancePOJO;
import com.eduhub.company.model.SetAttendancePOJO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    Context context;
    ArrayList<AttendancePOJO> arrayList;
    AttendancePOJO attendancePOJO;
    public static ArrayList<SetAttendancePOJO> attendancePOJOArrayList = new ArrayList<>();
    SetAttendancePOJO setAttendancePOJO = new SetAttendancePOJO();
    SetAttendancePOJO setAttendancePOJO1;
    String TAG = "1234AttendanceAdapter";
    boolean flag;

    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_attendance, viewGroup, false);
        AttendanceAdapter.ViewHolder viewHolder = new AttendanceAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    public AttendanceAdapter(ArrayList<AttendancePOJO> arrayList , Context context){
        this.arrayList  = arrayList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.ViewHolder viewHolder, final int i) {

        attendancePOJO = arrayList.get(i);
        Log.d(TAG, "onBindViewHolderAdapter: " + attendancePOJO.getStudentName());

        final String name = attendancePOJO.getStudentName();
        final String id = attendancePOJO.getStudentId();
        final String imgurl = attendancePOJO.getImgUrl();
        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        flag = false;

        viewHolder.attendanceSwitch.setChecked(false);
        viewHolder.studentName.setText(name);
//
        setAttendancePOJO.setStudentNameAtt(name);
        setAttendancePOJO.setStudentIdAtt(id);
        setAttendancePOJO.setIs_presentAtt(false);
        attendancePOJOArrayList.add(setAttendancePOJO);
        Log.d(TAG, "onBindViewHoldernNme: "+i+" "+attendancePOJO.getStudentName()+" "+setAttendancePOJO.getStudentNameAtt());
//
        Glide.with(context).load(imgurl).into(viewHolder.img);
        viewHolder.attendanceSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean on = ((Switch) v).isChecked();
                setAttendancePOJO1 = attendancePOJOArrayList.get(i);
                if (on) {
                    Toast.makeText(context, "present", Toast.LENGTH_LONG).show();
                    setAttendancePOJO1.setIs_presentAtt(true);
                    setAttendancePOJO.setStudentNameAtt(name);
                    setAttendancePOJO1.setStudentIdAtt(id);
                    attendancePOJOArrayList.set(i, setAttendancePOJO1);
                    Log.d(TAG, "onBindViewHolderAttendance: " + i + " " + setAttendancePOJO1.getStudentNameAtt() + "   " +
                            setAttendancePOJO1.getIs_presentAtt());
                }
                else {
                    Toast.makeText(context, "absent", Toast.LENGTH_LONG).show();
                    setAttendancePOJO1.setIs_presentAtt(false);
                    setAttendancePOJO1.setStudentNameAtt(name);
                    setAttendancePOJO1.setStudentIdAtt(id);
                    attendancePOJOArrayList.set(i,setAttendancePOJO1);
                    Log.d(TAG, "onBindViewHolderAttendance: " + attendancePOJO.getStudentName()+"   "+attendancePOJO.getIs_present());
                }
                String id = context.getSharedPreferences("mypref", Context.MODE_PRIVATE).getString("id",null);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("teacher").child(id)
                        .child("data").child("attendance");
                databaseReference.child(date).push().setValue(setAttendancePOJO1);
            }
        });
        Log.d(TAG, "onBindViewHolder: awsedrftghjkhgvfcxdzdcfgvhbjnmknjbvcx");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        Switch attendanceSwitch;
        TextView studentName;
        CircleImageView img;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            attendanceSwitch = itemView.findViewById(R.id.attendanceSwitch);
            studentName = itemView.findViewById(R.id.studentName);
            img = itemView.findViewById(R.id.attendanceImg);
        }
    }
}
