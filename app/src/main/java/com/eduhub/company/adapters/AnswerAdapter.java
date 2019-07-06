package com.eduhub.company.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eduhub.company.R;
import com.eduhub.company.model.AnswerPOJO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
    ArrayList<AnswerPOJO> arrayList;
    Context context;
    public AnswerAdapter(ArrayList<AnswerPOJO> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.comment_item, viewGroup, false);
        AnswerAdapter.ViewHolder viewHolder = new AnswerAdapter.ViewHolder(listItem);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.ViewHolder viewHolder, int position) {
        AnswerPOJO answerPOJO = arrayList.get(position);
        viewHolder.answer.setText(answerPOJO.getAnswer());
        viewHolder.name.setText(answerPOJO.getUserName());
        Glide.with(context).load(answerPOJO.getUserProfileUrl()).into(viewHolder.profilePic);
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profilePic;
        TextView name, answer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.commentUserProfile);
            name = itemView.findViewById(R.id.commentUserName);
            answer = itemView.findViewById(R.id.textViewComment);
        }
    }
}
