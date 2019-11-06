package com.thebhakti;

/**
 * Created by Yeshveer on 8/18/2018.
 */

class DetailsofP {
    String prodImageName,prodDetails,prodPrice,Prodname1;
    public DetailsofP(String prodImageName, String prodDetails,String prodPrice,String Prodname1) {
        this.prodDetails=prodDetails;
        this.prodImageName=prodImageName;
        this.prodPrice=prodPrice;
        this.Prodname1=Prodname1;

    }

    // Create Getter Method

    public String getProdImageName() {
        return prodImageName;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public String getProdname1() {
        return Prodname1;
    }

    public String getProdDetails() {
        return prodDetails;
    }


    // Create Stter Method

    public void setProdImageName(String prodImageName) {
        this.prodImageName = prodImageName;
    }

    public void setProdDetails(String prodDetails) {
        this.prodDetails = prodDetails;
    }

    public void setProdname1(String prodname1) {
        Prodname1 = prodname1;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }
}
