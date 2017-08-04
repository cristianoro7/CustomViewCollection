package com.desperado.customviewcollection.chatview;

/**
 * Created by desperado on 17-8-2.
 */

public class ChatBean {

    private String date;

    private String data;

    public ChatBean(String date, String data) {
        this.date = date;
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
