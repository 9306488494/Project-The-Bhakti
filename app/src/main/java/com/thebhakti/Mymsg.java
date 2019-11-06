package com.thebhakti;

/**
 * Created by Yeshveer on 9/3/2018.
 */

public class Mymsg {
    private String fetch_msg;
    private String checkSeen;
    private String babamsg;
    private String time;
    Mymsg(String fetch_msg, String babamsg,String checkSeen,String time) {
        this.fetch_msg=fetch_msg;
        this.babamsg=babamsg;
        this.checkSeen=checkSeen;
        this.time=time;
    }


    // Getter and Stter Method

    public String getBabamsg() {
        return babamsg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setBabamsg(String babamsg) {
        this.babamsg = babamsg;
    }

    public String getFetch_msg() {
        return fetch_msg;
    }

    public void setFetch_msg(String fetch_msg) {
        this.fetch_msg = fetch_msg;
    }

    public String getCheckSeen() {
        return checkSeen;
    }

    public void setCheckSeen(String checkSeen) {
        this.checkSeen = checkSeen;
    }
}
