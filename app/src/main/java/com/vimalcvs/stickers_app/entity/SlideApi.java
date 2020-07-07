package com.vimalcvs.stickers_app.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SlideApi {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("pack")
    @Expose
    private PackApi pack;
    @SerializedName("category")
    @Expose
    private CategoryApi category;
    @SerializedName("url")
    @Expose
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public PackApi getPack() {
        return pack;
    }

    public void setPack(PackApi pack) {
        this.pack = pack;
    }

    public CategoryApi getCategory() {
        return category;
    }

    public void setCategory(CategoryApi category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}