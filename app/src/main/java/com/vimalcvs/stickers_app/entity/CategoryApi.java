package com.vimalcvs.stickers_app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vimal on 28/09/2017.
 */



public class CategoryApi {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("packs")
    @Expose
    private Integer packs;
    private int viewType = 1;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getViewType() {
        return viewType;
    }

    public CategoryApi setViewType(int viewType) {
        this.viewType = viewType;
        return this;
    }

    public void setPacks(Integer packs) {
        this.packs = packs;
    }

    public Integer getPacks() {
        return packs;
    }
}