package com.mobile.emojis.network;

import com.mobile.emojis.model.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MessagingService {

    @GET("EmojiMap.json")
    Call<List<Data>> fetchEmojisMap();
}
