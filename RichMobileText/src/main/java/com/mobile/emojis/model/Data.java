package com.mobile.emojis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @Expose
    @SerializedName("type")
    private String type;

    @Expose
    @SerializedName("icons")
    private List<Emoji> icons;

    public Data(final String type, final List<Emoji> icons) {
        this.type = type;
        this.icons = icons;
    }

    public String getType() {
        return type;
    }

    public List<Emoji> getIcons() {
        return icons;
    }
}
