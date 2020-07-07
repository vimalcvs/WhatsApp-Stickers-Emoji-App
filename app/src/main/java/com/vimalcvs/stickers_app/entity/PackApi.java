package com.vimalcvs.stickers_app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vimalcvs.stickers_app.StickerPack;

import java.util.ArrayList;
import java.util.List;

public class PackApi {


    @SerializedName("identifier")
    @Expose
    private Integer identifier;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("publisher")
    @Expose
    private String publisher;
    @SerializedName("tray_image_file")
    @Expose
    private String trayImageFile;
    @SerializedName("publisher_email")
    @Expose
    private String publisherEmail;
    @SerializedName("publisher_website")
    @Expose
    private String publisherWebsite;
    @SerializedName("privacy_policy_website")
    @Expose
    private String privacyPolicyWebsite;
    @SerializedName("license_agreement_website")
    @Expose
    private String licenseAgreementWebsite;
    @SerializedName("premium")
    @Expose
    private String premium;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("downloads")
    @Expose
    private String downloads;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("userimage")
    @Expose
    private String userimage;
    @SerializedName("trusted")
    @Expose
    private String trusted;
    @SerializedName("stickers")
    @Expose
    private List<StickerApi> stickers = null;

    public PackApi() {
    }

    public PackApi(StickerPack stickerPack) {
        this.identifier =  Integer.parseInt(stickerPack.identifier);
        this.name =  stickerPack.name;
        this.publisher =  stickerPack.publisher;
        this.trayImageFile =  stickerPack.trayImageUrl;
        this.publisherEmail =  stickerPack.publisherEmail;
        this.publisherWebsite =  stickerPack.publisherWebsite;
        this.privacyPolicyWebsite =  stickerPack.privacyPolicyWebsite;
        this.licenseAgreementWebsite = stickerPack.licenseAgreementWebsite;
        this.premium = stickerPack.premium;
        this.downloads =  stickerPack.downloads;
        this.size =  stickerPack.size;
        this.created =  stickerPack.created;
        this.user =  stickerPack.username;
        this.userid = stickerPack.userid;
        this.userimage =  stickerPack.username;
        this.trusted = stickerPack.trused;
        this.stickers = new ArrayList<StickerApi>();
        for (int i = 0; i < stickerPack.getStickers().size(); i++) {
            StickerApi stickerApi  =  new StickerApi();
            stickerApi.setImageFile(stickerPack.getStickers().get(i).imageFileUrl);
            this.stickers.add(stickerApi);
        }
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTrayImageFile() {
        return trayImageFile;
    }

    public void setTrayImageFile(String trayImageFile) {
        this.trayImageFile = trayImageFile;
    }

    public String getPublisherEmail() {
        return publisherEmail;
    }

    public void setPublisherEmail(String publisherEmail) {
        this.publisherEmail = publisherEmail;
    }

    public String getPublisherWebsite() {
        return publisherWebsite;
    }

    public void setPublisherWebsite(String publisherWebsite) {
        this.publisherWebsite = publisherWebsite;
    }

    public String getPrivacyPolicyWebsite() {
        return privacyPolicyWebsite;
    }

    public void setPrivacyPolicyWebsite(String privacyPolicyWebsite) {
        this.privacyPolicyWebsite = privacyPolicyWebsite;
    }

    public String getLicenseAgreementWebsite() {
        return licenseAgreementWebsite;
    }

    public void setLicenseAgreementWebsite(String licenseAgreementWebsite) {
        this.licenseAgreementWebsite = licenseAgreementWebsite;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public List<StickerApi> getStickers() {
        return stickers;
    }

    public void setStickers(List<StickerApi> stickers) {
        this.stickers = stickers;
    }

    public void setTrusted(String trusted) {
        this.trusted = trusted;
    }

    public String getTrusted() {
        return trusted;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReview() {
        return review;
    }
}
