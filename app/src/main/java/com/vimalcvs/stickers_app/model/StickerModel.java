package com.vimalcvs.stickers_app.model;

import java.util.List;

public class StickerModel {

    public String stickerName;
    public String image_one;
    public String image_two;
    public String image_three;
    public String image_four;
    public List<String> downloadFile;

    public StickerModel(String stickerName, String image_one, String image_two, String image_three, String image_four, List<String> downloadFile) {
        this.stickerName = stickerName;
        this.image_one = image_one;
        this.image_two = image_two;
        this.image_three = image_three;
        this.image_four = image_four;
        this.downloadFile = downloadFile;
    }

    public String getStickerName() {
        return stickerName;
    }

    public void setStickerName(String stickerName) {
        this.stickerName = stickerName;
    }

    public String getImage_one() {
        return image_one;
    }

    public void setImage_one(String image_one) {
        this.image_one = image_one;
    }

    public String getImage_two() {
        return image_two;
    }

    public void setImage_two(String image_two) {
        this.image_two = image_two;
    }

    public String getImage_three() {
        return image_three;
    }

    public void setImage_three(String image_three) {
        this.image_three = image_three;
    }

    public String getImage_four() {
        return image_four;
    }

    public void setImage_four(String image_four) {
        this.image_four = image_four;
    }

    public List<String> getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(List<String> downloadFile) {
        this.downloadFile = downloadFile;
    }
}

