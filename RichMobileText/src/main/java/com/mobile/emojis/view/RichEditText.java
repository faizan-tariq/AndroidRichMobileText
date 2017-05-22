package com.mobile.emojis.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.mobile.emojis.listener.EditableAttributes;
import com.mobile.emojis.listener.FontSizeChangedListener;
import com.mobile.emojis.model.Emoji;
import com.mobile.emojis.util.Utils;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RichEditText extends EditText implements EditableAttributes, View.OnFocusChangeListener{

    private Context context;
    private boolean customEmojiEnabled;
    private boolean textSizeChangeable;
    private float textSize;
    private View controlBar;
    private ControlBarWindow controlBarWindow;

    private FontSizeChangedListener listener;

    public RichEditText(final Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RichEditText(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public RichEditText(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init(){
        setInputType(getInputType()
                | EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        setOnFocusChangeListener(this);
        if(customEmojiEnabled || textSizeChangeable) {
            if(controlBar!=null) {
                if (controlBarWindow == null) {
                    controlBarWindow = new ControlBarWindow(context, ((Activity) context).getWindow().getDecorView().getRootView(), controlBar, this, customEmojiEnabled, textSizeChangeable);
                } else {
                    controlBarWindow.setTextSizeChangeable(textSizeChangeable);
                    controlBarWindow.setCustomEmojiEnabled(customEmojiEnabled);
                }
            }
        }
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start, final int lengthBefore, final int lengthAfter) {
        Spannable s = getTextWithImages(getContext(), text, this.getLineHeight());
        super.onTextChanged(s, start, lengthBefore, lengthAfter);
    }

    @Override
    public void setText(final CharSequence text, final BufferType type) {
        String escapedText = StringEscapeUtils.unescapeJava(text.toString());
        SpannableStringBuilder s = getTextWithImages(getContext(), escapedText, this.getLineHeight());
        super.setText(s, BufferType.SPANNABLE);
    }

    private boolean addImages(final Context context, final Spannable spannable, final float height){
        return Utils.invalidateRichText(context, spannable, (int) height, customEmojiEnabled, true);
    }

    private SpannableStringBuilder getTextWithImages(final Context context, final CharSequence text, final float height) {
        SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder.valueOf(text);
        addImages(context, spannableStringBuilder, height);
        return spannableStringBuilder;
    }

    @Override
    public void customEmojiEnabled(boolean flag) {
        this.customEmojiEnabled = flag;
        init();
    }

    @Override
    public void textSizeChangeable(boolean flag, FontSizeChangedListener listener) {
        this.textSizeChangeable = flag;
        this.listener = listener;
        init();
    }

    @Override
    public void setControlBar(View view) {
        controlBar = view;
        init();
    }

    public String getEscapedJavaText() {
        return  StringEscapeUtils.escapeJava(super.getText().toString());
    }

    public void setChangedTextSize(int dimen){
        this.textSize = context.getResources().getDimension(dimen) / getResources().getDisplayMetrics().density;
        updateFontSize();
    }

    private void updateFontSize() {
        setTextSize(this.textSize);
        int start = getSelectionStart();
        int end = getSelectionEnd();
        setText(getTextWithImages(getContext(), getText(), this.getLineHeight()));
        setSelection(start, end);

        if(listener!=null){
            listener.onFontSizeChaned();
        }
    }

    public float getCurrentTextSize(){
        return this.textSize;
    }

    public void setCurrentTextSize(float textSize){
        this.textSize = textSize;
        updateFontSize();
    }

    @Override
    public void onFocusChange(View view, boolean focus) {
        if(controlBarWindow != null && controlBar != null){
            if(!focus){
                controlBar.setVisibility(View.GONE);
                controlBarWindow.getEmojiPopupWindow().dismiss();
            }else{
                controlBarWindow.show();
            }
        }
    }
}
