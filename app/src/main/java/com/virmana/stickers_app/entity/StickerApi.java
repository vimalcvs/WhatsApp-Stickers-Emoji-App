package com.virmana.stickers_app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StickerApi {


    @SerializedName("image_file")
    @Expose
    private String imageFile;

    @SerializedName("image_file_thum")
    @Expose
    private String imageFileThum;

    @SerializedName("emojis")
    @Expose
    private List<String> emojis;

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public List<String> getEmojis() {
        return emojis;
    }

    public void setEmojis(List<String> emojis) {
        this.emojis = emojis;
    }

    public void setImageFileThum(String imageFileThum) {
        this.imageFileThum = imageFileThum;
    }
    public String getImageFileThum() {
        return imageFileThum;
    }
}

