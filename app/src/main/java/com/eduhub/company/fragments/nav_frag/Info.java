package com.eduhub.company.fragments.nav_frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eduhub.company.R;
import com.eduhub.company.activities.InfoTab.Assignment;
import com.eduhub.company.activities.InfoTab.Attendance;
import com.eduhub.company.activities.InfoTab.Marks;
import com.eduhub.company.activities.InfoTab.Projects;
import com.eduhub.company.activities.InfoTab.Syllabus;
import com.eduhub.company.activities.InfoTab.TimeTable;
import com.eduhub.company.activities.ReportIssue;

public class Info extends Fragment {

    CardView attendance,assignment,projects,marks,timeTable,syllabus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info,null,false);

        attendance = view.findViewById(R.id.attendance);
        assignment  = view.findViewById(R.id.assignment);
        projects = view.findViewById(R.id.projects);
        marks = view.findViewById(R.id.marks);
        timeTable = view.findViewById(R.id.timeTable);
        syllabus = view.findViewById(R.id.syllabus);
        TextView report = view.findViewById(R.id.report);

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Attendance.class);
                startActivity(intent);
            }
        });
        assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Assignment.class);
                startActivity(intent);
            }
        });
        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Marks.class);
                startActivity(intent);
            }
        });
        projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Projects.class);
                startActivity(intent);
            }
        });
        timeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TimeTable.class);
                startActivity(intent);
            }
        });
        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Syllabus.class);
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ReportIssue.class));
            }
        });
        
        return view;
    }
}
