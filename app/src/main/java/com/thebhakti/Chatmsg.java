package com.thebhakti;

/**
 * Created by Yeshveer on 9/2/2018.
 */

public class Chatmsg {
    private String msgTxt;
    private String msgUser;


    public Chatmsg(String msgTxt, String msgUser) {
        this.msgTxt = msgTxt;
        this.msgUser = msgUser;

    }

    // Create Contructor

    public Chatmsg() {
    }

    // Call Getter and Setter Method

    public String getMsgTxt() {
        return msgTxt;
    }

    public void setMsgTxt(String msgTxt) {
        this.msgTxt = msgTxt;
    }

    public String getMsgUser() {
        return msgUser;
    }

    public void setMsgUser(String msgUser) {
        this.msgUser = msgUser;
    }

}
