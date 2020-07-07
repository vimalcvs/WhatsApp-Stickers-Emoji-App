package com.vimalcvs.stickers_app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vimalcvs on 27/11/2017.
 */


public class ApiResponse {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("values")
    @Expose
    private List<ApiValue> values = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ApiValue> getValues() {
        return values;
    }

    public void setValues(List<ApiValue> values) {
        this.values = values;
    }
}
