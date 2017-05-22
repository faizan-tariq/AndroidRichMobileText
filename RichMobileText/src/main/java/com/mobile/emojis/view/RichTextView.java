package com.mobile.emojis.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.commonsware.cwac.anddown.AndDown;
import com.mobile.emojis.listener.TextualAttributes;
import com.mobile.emojis.model.Emoji;
import com.mobile.emojis.util.Utils;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RichTextView extends TextView implements TextualAttributes{

    private boolean htmlEnabled;
    private boolean markdownEnabled;

    public RichTextView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

    }
    public RichTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }
    public RichTextView(final Context context) {
        super(context);
    }

    @Override
    public void setText(final CharSequence text, final BufferType type) {
        Spannable s = new SpannableString(StringEscapeUtils.unescapeJava(text.toString()));

        try {
            AndDown andDown=new AndDown();
            if(markdownEnabled) {
                s = new SpannableString(andDown.markdownToHtml(s.toString()));
            }
            if(markdownEnabled || htmlEnabled) {
                s = new SpannableString(Html.fromHtml(s.toString().replaceAll("\\n","<br/>")));
            }
            s = getTextWithImages(getContext(), s, this.getLineHeight());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.setText(s, BufferType.SPANNABLE);
    }

    private boolean addImages(final Context context, final Spannable spannable, final float height){
        return Utils.invalidateRichText(context, spannable, (int) height, false, false);
    }

    private Spannable getTextWithImages(final Context context, final CharSequence text, final float height) throws ExecutionException, InterruptedException {
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(text);
        addImages(context, spannable, height);
        return spannable;
    }

    @Override
    public void htmlEnabled(boolean flag) {
        this.htmlEnabled = flag;
    }

    @Override
    public void markdownEnabled(boolean flag) {
        this.markdownEnabled = flag;
    }

    public String getEscapedJavaText() {
        return  StringEscapeUtils.escapeJava(super.getText().toString());
    }
}
