package com.mobile.emojis.service;

import android.app.IntentService;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.mobile.emojis.config.Keys;
import com.mobile.emojis.model.Data;
import com.mobile.emojis.model.Emoji;
import com.mobile.emojis.network.RetrofitClient;
import com.mobile.emojis.pref.ClientPreference;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;

public class SyncEmojis extends IntentService {

    public SyncEmojis(){
        super(SyncEmojis.class.getName());
    }

    public SyncEmojis(final String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        try {
            Call<List<Data>> call = new RetrofitClient(intent.getStringExtra("url")).getMessagingService().fetchEmojisMap();
            Response< List<Data>> response = call.execute();
            if(response.isSuccessful()){
                List<Data> result = response.body();
                for(Data data: result){
                    List<Emoji> emojis = data.getIcons();
                    for(Emoji emoji: emojis){
                        File file = loadFileFromUrl(emoji.getUrl());
                        if(file!=null){
                            emoji.setLocalCachedPath(file.getAbsolutePath());
                        }
                    }
                }

                Gson gson = new Gson();
                String cachedResponse = gson.toJson(result);
                ClientPreference.getInstance().putString(getApplicationContext(), Keys.EMOJI_MAP.getKey() , cachedResponse);
            }
        } catch (IOException e) {

        }
    }

    private File loadFileFromUrl(final String url){
        try {
            FutureTarget<File> future = Glide.with(getApplicationContext())
                .load(url)
                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            return future.get();
        } catch (InterruptedException e) {
            return null;
        } catch (ExecutionException e) {
            return null;
        }
    }
}
