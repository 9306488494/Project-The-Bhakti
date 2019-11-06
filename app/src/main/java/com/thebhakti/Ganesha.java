package com.thebhakti;

/**
 * Created by Yeshveer on 8/10/2018.
 */

public class Ganesha {
    private String photo;
    private String Title;
    private String DocId;
    private String Desp;
    private String DespTitle;
    private String MetaData;
    private String Product;


    public Ganesha(String DespTitle,String Desp,String DocId,String Title,String photo,String MetaData,String Product) {
        this.photo = photo;
        this.Title=Title;
        this.DocId=DocId;
        this.Desp=Desp;
        this.DespTitle=DespTitle;
        this.MetaData=MetaData;
        this.Product=Product;

    }
    // Getter Method for get The Image value

    public String getPhoto() {
        return photo;
    }

    public String getDesp() {
        return Desp;
    }

    public String getDespTitle() {
        return DespTitle;
    }

    public String getDocId() {
        return DocId;
    }

    public String getMetaData() {
        return MetaData;
    }

    public void setMetaData(String metaData) {
        MetaData = metaData;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getTitle() {
        return Title;
    }
// Setter Method for get The Image value

    public void setDespTitle(String despTitle) {
        DespTitle = despTitle;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDesp(String desp) {
        Desp = desp;
    }

    public void setDocId(String docId) {
        DocId = docId;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
