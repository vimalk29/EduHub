package com.eduhub.company.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.eduhub.company.R;
import com.eduhub.company.activities.Comments;
import com.eduhub.company.helper.DatabaseManagement;
import com.eduhub.company.model.AnswerPOJO;
import com.eduhub.company.model.QuestionPOJO;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    ArrayList<QuestionPOJO> arrayList;
    Context context;
    DatabaseManagement databaseManagement;
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.question_item, viewGroup, false);
        QuestionAdapter.ViewHolder viewHolder = new QuestionAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    public QuestionAdapter (ArrayList<QuestionPOJO> questionPOJOarrayList, Context context){
        this.arrayList = questionPOJOarrayList;
        this.context = context;
    }
    @Override
    public void onBindViewHolder(final QuestionAdapter.ViewHolder viewHolder, final int position) {
        databaseManagement = new DatabaseManagement(context);
        final QuestionPOJO questionPOJO = arrayList.get(position);
        viewHolder.name.setText(questionPOJO.getName());
        viewHolder.question.setText(questionPOJO.getQuestion());
        viewHolder.count.setText(questionPOJO.getCount() + "");
        Glide.with(context).load(questionPOJO.getImageUrl()).into(viewHolder.profilePic);
        viewHolder.textViewAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Comments.class);
                intent.putExtra("id", questionPOJO.getQuestionId());
                context.startActivity(intent);
            }
        });
        viewHolder.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = context.getSharedPreferences("mypref", Context.MODE_PRIVATE).getString("name", null);
                String profilePicUrl = context.getSharedPreferences("mypref", Context.MODE_PRIVATE).getString("profilePic", null);
                AnswerPOJO answerPOJO = new AnswerPOJO();
                answerPOJO.setAnswer(viewHolder.answereditText.getText().toString());
                answerPOJO.setUserName(name);
                answerPOJO.setUserProfileUrl(profilePicUrl);
                databaseManagement.uploadAnswer(questionPOJO.getQuestionId(), answerPOJO);
            }
        });

        if (questionPOJO.isIs_likes() == false){
            viewHolder.countUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = questionPOJO.getCount();
                    databaseManagement.updateCount(count + 1, questionPOJO.getQuestionId());
                    questionPOJO.setCount(++count);
                    viewHolder.count.setText(questionPOJO.getCount() + "");
                    questionPOJO.setIs_likes(true);
                    questionPOJO.setIs_dis_likes(false);
                    arrayList.get(position).setIs_dis_likes(false);
                    arrayList.get(position).setIs_likes(true);
                    notifyDataSetChanged();
                }
            });
        }
        if (questionPOJO.isIs_dis_likes() == false) {
            viewHolder.countDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = questionPOJO.getCount();
                    databaseManagement.updateCount(count - 1, questionPOJO.getQuestionId());
                    questionPOJO.setCount(--count);
                    viewHolder.count.setText(questionPOJO.getCount() + "");
                    questionPOJO.setIs_dis_likes(true);
                    questionPOJO.setIs_likes(false);
                    arrayList.get(position).setIs_dis_likes(true);
                    arrayList.get(position).setIs_likes(false);
                    notifyDataSetChanged();
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profilePic;
        TextView name, question, count, textViewAnswers;
        TextInputEditText answereditText;
        ImageButton sendButton;
        ImageView countUp, countDown;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAnswers = itemView.findViewById(R.id.textViewAnswers);
            profilePic = itemView.findViewById(R.id.userProfile);
            name = itemView.findViewById(R.id.userId);
            question = itemView.findViewById(R.id.userQues);
            count = itemView.findViewById(R.id.likesNumber);
            answereditText = itemView.findViewById(R.id.editTextAnswer);
            sendButton = itemView.findViewById(R.id.buttonSend);
            countUp = itemView.findViewById(R.id.imageLike);
            countDown = itemView.findViewById(R.id.imageDislike);
        }
    }
}
