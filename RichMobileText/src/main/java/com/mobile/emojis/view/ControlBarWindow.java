package com.mobile.emojis.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.emojis.R;
import com.mobile.emojis.util.Utils;

public class ControlBarWindow implements View.OnClickListener{

    private boolean customEmojiEnabled;
    private boolean textSizeChangeable;
    private View popupView;
    private Context context;
    private View rootView;
    private RichEditText editText;
    private EmojiPopupWindow emojiPopupWindow;
    private TextView defaultFont;
    private TextView mediumFont;
    private TextView largeFont;
    private LinearLayout layoutEmojisDisplay;
    private LinearLayout layoutFontSize;

    public ControlBarWindow(final Context context, final View rootView, final View popupView, final RichEditText editText, final boolean customEmojiEnabled, final boolean textSizeChangeable){
        this.context = context;
        this.rootView = rootView;
        this.editText = editText;
        this.popupView = popupView;
        this.customEmojiEnabled = customEmojiEnabled;
        this.textSizeChangeable = textSizeChangeable;
        createCustomView();
        getDynamicHeight();
    }

    private void getDynamicHeight(){
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                show();
            }
        });
    }

    public void show() {
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        int screenHeight = Utils.getUsableScreenHeight(context, rootView);
        int heightDifference = screenHeight - (r.bottom - r.top);
        heightDifference -= Utils.getStatusBarHeight(context);

        if (heightDifference > 100 && editText.isFocused()) {
            if(customEmojiEnabled || textSizeChangeable) {
                popupView.setVisibility(View.VISIBLE);
            }
        } else {
            popupView.setVisibility(View.GONE);
            getEmojiPopupWindow().dismiss();
        }
    }

    private void createCustomView(){
        defaultFont = (TextView) popupView.findViewById(R.id.default_font);
        mediumFont = (TextView) popupView.findViewById(R.id.medium_font);
        largeFont = (TextView) popupView.findViewById(R.id.large_font);
        layoutEmojisDisplay = (LinearLayout)popupView.findViewById(R.id.emojis);
        layoutFontSize = (LinearLayout) popupView.findViewById(R.id.font_size);

        layoutEmojisDisplay.setOnClickListener(this);
        defaultFont.setOnClickListener(this);
        mediumFont.setOnClickListener(this);
        largeFont.setOnClickListener(this);

        initInterface();
    }

    private void initInterface(){
        layoutEmojisDisplay.setVisibility(customEmojiEnabled ? View.VISIBLE : View.INVISIBLE);
        layoutFontSize.setVisibility(textSizeChangeable ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.emojis){
            getEmojiPopupWindow().show();
        }else if(id == R.id.default_font){
            editText.setChangedTextSize(R.dimen.changeable_font_size_default);
        }else if(id == R.id.medium_font){
            editText.setChangedTextSize(R.dimen.changeable_font_size_medium);
        }else if(id == R.id.large_font){
            editText.setChangedTextSize(R.dimen.changeable_font_size_large);
        }
    }

    public EmojiPopupWindow getEmojiPopupWindow() {
        if(emojiPopupWindow == null){
            emojiPopupWindow = new EmojiPopupWindow(context, ((Activity)context).getWindow().getDecorView().getRootView(), editText);
        }
        return emojiPopupWindow;
    }

    public void setCustomEmojiEnabled(boolean customEmojiEnabled) {
        this.customEmojiEnabled = customEmojiEnabled;
        initInterface();
    }

    public void setTextSizeChangeable(boolean textSizeChangeable) {
        this.textSizeChangeable = textSizeChangeable;
        initInterface();
    }
}
