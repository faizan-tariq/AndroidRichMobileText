package com.mobile.emojis.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.mobile.emojis.R;
import com.mobile.emojis.adapter.SlidePagerAdapter;
import com.mobile.emojis.listener.OnEmojiClickListener;
import com.mobile.emojis.model.Emoji;
import com.mobile.emojis.util.Utils;

public class EmojiPopupWindow extends PopupWindow implements OnEmojiClickListener, View.OnClickListener{

    private View popupView;
    private Context context;
    private ViewPager pager;
    private SlidePagerAdapter adapt;
    private View rootView;
    private RichEditText editText;

    private ImageView showKeyboard;
    private ImageView backspace;
    private Button space;

    public EmojiPopupWindow(final Context context, final View rootView, final RichEditText editText){
        super(context);
        this.context = context;
        this.rootView = rootView;
        this.editText = editText;
        createCustomView();
        setContentView(popupView);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setBackgroundDrawable(null);
    }

    private void renderLayout() {
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        int screenHeight = Utils.getUsableScreenHeight(context, rootView);
        int heightDifference = screenHeight - (r.bottom - r.top);
        heightDifference -= Utils.getStatusBarHeight(context);

        if (heightDifference > 100) {
            int keyBoardHeight = heightDifference;
            setSize(WindowManager.LayoutParams.MATCH_PARENT, keyBoardHeight);
            showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
        } else {
            dismiss();
        }
    }

    private void setSize(final int width, final int height){
        setWidth(width);
        setHeight(height);
    }

    private void createCustomView(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.viewpager, null, false);
        pager = (ViewPager) popupView.findViewById(R.id.pager);
        adapt = new SlidePagerAdapter(context, Utils.getEmojisList(context), this);
        pager.setAdapter(adapt);

        showKeyboard = (ImageView) popupView.findViewById(R.id.keyboard);
        backspace = (ImageView) popupView.findViewById(R.id.backspace);
        space = (Button) popupView.findViewById(R.id.space);

        showKeyboard.setOnClickListener(this);
        backspace.setOnClickListener(this);
        space.setOnClickListener(this);

    }

    public void show(){
        if(isShowing()){
            dismiss();
        }else{
            renderLayout();
        }
    }

    @Override
    public void onEmojiClicked(final Emoji emoji) {
        editText.getSelectionStart();
        editText.getText().insert(editText.getSelectionStart(), emoji.getCode());
    }

    @Override
    public void onClick(View view) {
        int clickedViewId = view.getId();
        if(clickedViewId == R.id.keyboard){
            dismiss();
        }else if(clickedViewId == R.id.space){
            editText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE));
        } else if(clickedViewId == R.id.backspace){
            editText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        }
    }
}
