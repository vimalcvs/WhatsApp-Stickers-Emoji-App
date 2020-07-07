package com.vimalcvs.stickers_app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vimal on 05/04/2018.
 */

public class TagApi {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("search")
    @Expose
    private Integer search;

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

    public Integer getSearch() {
        return search;
    }

    public void setSearch(Integer search) {
        this.search = search;
    }

}
