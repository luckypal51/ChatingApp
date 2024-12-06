package com.example.messengerapp.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class Userchatroom {
     String chatroomId;
     List<String> usersId;
    Timestamp LastMessageTimestamp;
    String LastMessageSenderId;
    String lastMssage;

    public Userchatroom() {
    }

    public Userchatroom(String chatroomId, List<String> usersId, Timestamp lastMessageTimestamp, String lastMessageSenderId) {
        this.chatroomId = chatroomId;
        this.usersId = usersId;
        LastMessageTimestamp = lastMessageTimestamp;
        LastMessageSenderId = lastMessageSenderId;
    }

    public Timestamp getLastMessageTimestamp() {
        return LastMessageTimestamp;
    }

    public String getLastMssage() {
        return lastMssage;
    }

    public void setLastMssage(String lastMssage) {
        this.lastMssage = lastMssage;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public List<String> getUsersId() {
        return usersId;
    }

    public void setUsersId(List<String> usersId) {
        this.usersId = usersId;
    }

    public Timestamp getLastMessageTimestamp(Timestamp now) {
        return LastMessageTimestamp;
    }

    public void setLastMessageTimestamp(Timestamp lastMessageTimestamp) {
        LastMessageTimestamp = lastMessageTimestamp;
    }

    public String getLastMessageSenderId() {
        return LastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        LastMessageSenderId = lastMessageSenderId;
    }
}
