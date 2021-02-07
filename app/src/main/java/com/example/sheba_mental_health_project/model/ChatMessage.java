package com.example.sheba_mental_health_project.model;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class ChatMessage implements Comparable<ChatMessage> {

    private String mContent;
    private String mRecipientEmail;
    private Date mTime;

    public ChatMessage() {}

    public ChatMessage(final String mContent, final String mRecipientEmail) {
        this.mContent = mContent;
        this.mRecipientEmail = mRecipientEmail;
        this.mTime = new Date();
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public String getRecipientEmail() {
        return mRecipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.mRecipientEmail = recipientEmail;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        this.mTime = time;
    }


    @Override
    public String toString() {
        return "ChatMessage {" +
                "Content=" + mContent +
                ", RecipientEmail=" + mRecipientEmail +
                ", Time=" + mTime +
                '}';
    }

    @Override
    public int compareTo(@NotNull ChatMessage otherMessage) {
        if (otherMessage != null) {
            return this.mTime.compareTo(otherMessage.getTime());
        }
        return 0;
    }
}
