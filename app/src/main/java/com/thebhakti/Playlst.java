package com.thebhakti;

/**
 * Created by Yeshveer on 8/29/2018.
 */

public class Playlst {
private String video_title;
private String channel_id;
private String video_id;
private String playlist_id;
private String image_name;
private String metaData;
private String product;
private String url;

    public Playlst(String video_title, String channel_id, String video_id, String playlist_id, String image_name, String metaData, String product,String url) {
    this.video_title=video_title;
    this.channel_id=channel_id;
    this.video_id=video_id;
    this.playlist_id=playlist_id;
    this.image_name=image_name;
    this.metaData=metaData;
    this.product=product;
    this.url=url;
    }
    // Getter and Setter Method


    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(String playlist_id) {
        this.playlist_id = playlist_id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
