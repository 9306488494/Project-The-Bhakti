package com.thebhakti;

/**
 * Created by Yeshveer on 9/26/2018.
 */

class Comments {
    String name,comment;
    public Comments(String name, String comment) {
        this.name=name;
        this.comment=comment;
    }

    /*Getter and setter method */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
