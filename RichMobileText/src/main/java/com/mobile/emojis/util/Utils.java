package com.mobile.emojis.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobile.emojis.config.Keys;
import com.mobile.emojis.model.Data;
import com.mobile.emojis.model.Emoji;
import com.mobile.emojis.pref.ClientPreference;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static Map<String, Emoji> map;

    public static List<Data> getEmojisList(final Context context){
        if(ClientPreference.getInstance().getString(context, Keys.EMOJI_MAP.getKey()) != null) {
            String mapString = ClientPreference.getInstance().getString(context, Keys.EMOJI_MAP.getKey());

            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Data>>(){}.getType();
            List<Data> list = gson.fromJson(mapString, collectionType);

            return list;
        }
        return null;
    }

    public static Map<String, Emoji>  getEmojisMap(final Context context){
        if(map == null || map.isEmpty()){
            createEmojisMap(context);
        }
        return map;
    }

    private static void createEmojisMap(final Context context){
        if(ClientPreference.getInstance().getString(context, Keys.EMOJI_MAP.getKey()) != null) {
            List<Data> list = getEmojisList(context);
            map = new HashMap<>();
            for (Data data : list) {
                for (Emoji emoji : data.getIcons()) {
                    map.put(emoji.getCode().toLowerCase(), emoji);
                }
            }
        }
    }

    public static int calculateNoOfColumns(final Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 40);
        return noOfColumns;
    }

    public static int getNavigationBarHeight(final Context context){
        if(!hasMenuKey(context) && !hasBackKey()) {
            return getStyleHeight(context, "navigation_bar_height");
        }
        return 0;
    }

    public static int getStatusBarHeight(final Context context){
        return getStyleHeight(context, "status_bar_height");
    }

    private static int getStyleHeight(Context context, String bar) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(bar, "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getUsableScreenHeight(final Context context, final View rootView) {
        if (!hasBackKey() && !hasMenuKey(context)) {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metrics);
            return metrics.heightPixels;
        } else {
            return rootView.getHeight();
        }
    }

    private static boolean hasMenuKey(final Context context){
        return ViewConfiguration.get(context).hasPermanentMenuKey();
    }

    private static boolean hasBackKey(){
        return KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
    }

    public static boolean invalidateRichText(Context context, Spannable spannable, int height, boolean customEmojiEnabled, boolean strictMode) {
        Pattern refImg = Pattern.compile(":\\w*");
        boolean hasChanges = false;

        if(customEmojiEnabled || !strictMode) {
            Matcher matcher = refImg.matcher(spannable);
            while (matcher.find()) {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(), matcher.end(), ImageSpan.class)) {
                    if (spannable.getSpanStart(span) >= matcher.start()
                            && spannable.getSpanEnd(span) <= matcher.end()
                            ) {
                        spannable.removeSpan(span);
                    } else {
                        set = false;
                        break;
                    }
                }
                String code = spannable.subSequence(matcher.start(0), matcher.end(0)).toString().trim().toLowerCase();
                Map<String, Emoji> map = Utils.getEmojisMap(context);
                if (map != null && map.containsKey(code)) {
                    Drawable mDrawable = Drawable.createFromPath(map.get(code).getLocalCahcedPath());
                    if(mDrawable != null) {
                        mDrawable.setBounds(0, 0, height, height);
                        if (set) {
                            hasChanges = true;
                            spannable.setSpan(new ImageSpan(mDrawable),
                                    matcher.start(),
                                    matcher.end(),
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            );
                        }
                    }
                }
            }
        }
        return hasChanges;
    }
}
