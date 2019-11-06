package com.thebhakti;

/**
 * Created by Yeshveer on 9/3/2018.
 */

public class ListedUsers {
    private String name,id,mobile,color,state,counter;
    ListedUsers(String name, String id, String mobile, String color,String state,String counter) {
        this.name=name;
        this.id=id;
        this.mobile=mobile;
        this.color=color;
        this.state=state;
        this.counter=counter;

    }
    // Getter and Setter Method

    public String getName() {
        return name;
    }


    String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String getMobile() {
        return mobile;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
