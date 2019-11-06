package com.thebhakti;

/**
 * Created by Yeshveer on 8/20/2018.
 */

class DetailsofR {
    String imgR,titleR,userR,MetaData1,descR,DocidR,Prodname1;

    public DetailsofR(String imgR, String titleR, String userR,String MetaData1,String descR,String DocidR,String Prodname1) {
        this.imgR=imgR;
        this.titleR=titleR;
        this.userR=userR;
        this.MetaData1=MetaData1;
        this.descR=descR;
        this.DocidR=DocidR;
        this.Prodname1=Prodname1;

    }
    // Getter Method

    public String getImgR() {
        return imgR;
    }

    public String getDescR() {
        return descR;
    }

    public String getDocidR() {
        return DocidR;
    }

    public String getProdname1() {
        return Prodname1;
    }

    public String getMetaData1() {
        return MetaData1;
    }

    public String getTitleR() {
        return titleR;
    }

    public String getUserR() {
        return userR;
    }


    // Setter Method


    public void setMetaData1(String metaData1) {
        MetaData1 = metaData1;
    }

    public void setImgR(String imgR) {
        this.imgR = imgR;
    }

    public void setDescR(String descR) {
        this.descR = descR;
    }

    public void setDocidR(String docidR) {
        DocidR = docidR;
    }

    public void setProdname1(String prodname1) {
        Prodname1 = prodname1;
    }

    public void setTitleR(String titleR) {
        this.titleR = titleR;
    }

    public void setUserR(String userR) {
        this.userR = userR;
    }
}
