package com.mobile.emojis.config;

/**
 *  Keys.
 */
public enum Keys {

    EMOJI_MAP("emojiMap");

    private String key = "";

    Keys(final String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
