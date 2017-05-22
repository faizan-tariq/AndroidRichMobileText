package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mobile.emojis.service.SyncEmojis;
import com.mobile.emojis.view.RichEditText;
import com.mobile.emojis.view.RichTextView;

public class MainActivity extends AppCompatActivity {

    private RichEditText editText;

    private RichTextView text;
    private RichTextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, SyncEmojis.class);
        intent.putExtra("url","http://staging-ltd-emoji.s3.amazonaws.com/");
        startService(intent);


        editText = (RichEditText) findViewById(R.id.edit);
        editText.textSizeChangeable(true,null);
        editText.customEmojiEnabled(true);
        editText.setControlBar(findViewById(R.id.controlbar));
        editText.setChangedTextSize(R.dimen.changeable_font_size_default);

        text = (RichTextView) findViewById(R.id.text);
        text.markdownEnabled(true);

        text1 = (RichTextView) findViewById(R.id.text1);
        text1.htmlEnabled(true);


        String str = "## This is Markdown \n This is Markdown :happy \\uD83D\\uDE01";
        String str1 = "<b>This is Html</b><br>This is Html :alien \\uD83D\\uDE01";

        text.setText(str);
        text1.setText(str1);
    }
}
