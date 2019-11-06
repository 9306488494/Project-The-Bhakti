package com.thebhakti;

/**
 * Created by Yeshveer on 11/17/2018.
 */

class ChatMsg2 {
    String id,name,msg,userType,time,seen;
    public ChatMsg2(String id, String name, String msg, String userType, String time,String seen) {
        this.id=id;
        this.name=name;
        this.msg=msg;
        this.userType=userType;
        this.time=time;
        this.seen=seen;

    }

    // create gettter and setter method

    public String getId() {
        return id;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
