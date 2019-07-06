package com.eduhub.company.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.eduhub.company.R;
import com.eduhub.company.helper.DatabaseManagement;
import com.eduhub.company.model.AnswerPOJO;
import com.eduhub.company.model.QuestionPOJO;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    ArrayList<QuestionPOJO> arrayList;
    Context context;
    DatabaseManagement databaseManagement;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    public QuestionAdapter (ArrayList<QuestionPOJO> questionPOJOarrayList, Context context){
        this.arrayList = questionPOJOarrayList;
        this.context = context;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        databaseManagement = new DatabaseManagement(context);
        final QuestionPOJO  questionPOJO= arrayList.get(position);
        viewHolder.name.setText(questionPOJO.getName());
        viewHolder.question.setText(questionPOJO.getQuestion());
        viewHolder.count.setText(questionPOJO.getCount());
        Glide.with(context).load(questionPOJO.getImageUrl()).into(viewHolder.profilePic);
        viewHolder.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = viewHolder.answereditText.getText().toString();
                String name = ""; //TODO Sharedpreference se user ka name lo
                String profilePicUrl = "";//TODO user  dp url lo
                AnswerPOJO answerPOJO = new AnswerPOJO();
                answerPOJO.setAnswer(answer);
                answerPOJO.setUserName(name);
                answerPOJO.setUserProfileUrl(profilePicUrl);
               databaseManagement.uploadAnswer(questionPOJO.getQuestionId(), answerPOJO);

            }
        });
        viewHolder.countDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = questionPOJO.getCount();
                databaseManagement.updateCount(count+1, questionPOJO.getQuestionId());
            }
        });
        viewHolder.countUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = questionPOJO.getCount();
                databaseManagement.updateCount(count-1, questionPOJO.getQuestionId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView profilePic;
        public TextView name, question, count;
        public TextInputEditText answereditText;
        public ImageButton sendButton;
        public ImageView countUp, countDown;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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
