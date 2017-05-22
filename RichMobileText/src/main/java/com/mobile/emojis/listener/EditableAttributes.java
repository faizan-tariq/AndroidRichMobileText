package com.mobile.emojis.listener;

import android.view.View;

public interface EditableAttributes {

    void customEmojiEnabled(final boolean flag);
    void textSizeChangeable(final boolean flag, final FontSizeChangedListener listener);
    void setControlBar(final View view);
}
