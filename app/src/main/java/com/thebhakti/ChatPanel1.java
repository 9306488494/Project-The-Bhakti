package com.thebhakti;

/**
 * Created by Yeshveer on 8/25/2018.
 */

class ChatPanel1 {
    String user_Msg;
    String self_txt;

    public ChatPanel1(String user_Msg,String self_txt) {
        this.user_Msg=user_Msg;
        this.self_txt=self_txt;

    }
    // Create Getter and Setter Methods


    public String getUser_Msg() {
        return user_Msg;
    }

    public void setUser_Msg(String user_Msg) {
        this.user_Msg = user_Msg;
    }

    public String getSelf_txt() {
        return self_txt;
    }

    public void setSelf_txt(String self_txt) {
        this.self_txt = self_txt;
    }
}
