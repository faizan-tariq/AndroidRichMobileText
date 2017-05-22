package com.mobile.emojis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Emoji {

    @Expose
    @SerializedName("code")
    private String code;

    @Expose
    @SerializedName("url")
    private String url;

    private String localCachedPath;

    public Emoji(final String code, final String url) {
        this.code = code;
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }

    public String getLocalCahcedPath(){
        return localCachedPath;
    }

    public void setLocalCachedPath(final String path){
        this.localCachedPath = path;
    }
}
