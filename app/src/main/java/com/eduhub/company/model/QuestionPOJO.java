package com.eduhub.company.model;

public class QuestionPOJO {
    private String imageUrl;
    private String name;
    private String userId;
    private String question;
    private String questionId;
    private int count;
    private boolean is_likes;
    private boolean is_dis_likes;

    public boolean isIs_dis_likes() {
        return is_dis_likes;
    }

    public void setIs_dis_likes(boolean is_dis_likes) {
        this.is_dis_likes = is_dis_likes;
    }

    public boolean isIs_likes() {
        return is_likes;
    }

    public void setIs_likes(boolean is_likes) {
        this.is_likes = is_likes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
