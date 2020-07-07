package com.vimalcvs.stickers_app.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserApi {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("trusted")
    @Expose
    private Boolean trusted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setTrusted(Boolean trusted) {
        this.trusted = trusted;
    }

    public Boolean getTrusted() {
        return trusted;
    }
}