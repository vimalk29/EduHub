package com.eduhub.company.model;

public class ChatPOJO {
    private String recieverId;
    private String receiverName;
    private String receiverPicUrl;
    private String lastMessage;
    private Boolean unseen=true;

    public ChatPOJO(){}
    public ChatPOJO(ChatPOJO chatsPOJO){
        setUnseen(chatsPOJO.getUnseen());
        setRecieverId(chatsPOJO.getRecieverId());
        setReceiverName(chatsPOJO.getReceiverName());
        setReceiverPicUrl((chatsPOJO.getReceiverPicUrl()));
        setLastMessage(chatsPOJO.getLastMessage());

    }
    public String getReceiverName() {
        return receiverName;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPicUrl() {
        return receiverPicUrl;
    }

    public void setReceiverPicUrl(String receiverPicUrl) {
        this.receiverPicUrl = receiverPicUrl;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Boolean getUnseen() {
        return unseen;
    }

    public void setUnseen(Boolean unseen) {
        this.unseen = unseen;
    }
}
