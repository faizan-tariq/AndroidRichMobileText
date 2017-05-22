package com.mobile.emojis.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallback implements Callback {

    private int requestCode;
    private ServerConnectionListener listener;

    public RetrofitCallback(final ServerConnectionListener listener, final int requestCode) {
        this.listener = listener;
        this.requestCode = requestCode;
        listener.onPreExecute();
    }

    @Override
    public void onResponse(final Call call, final Response response) {
        if (response.isSuccessful()) {
            ServerResponse callResponse = new ServerResponse(requestCode, response);
            listener.onSuccess(callResponse);
        }else{
            listener.onFailure(requestCode);
        }
    }

    @Override
    public void onFailure(final Call call, final Throwable t) {
        listener.onFailure(requestCode);
    }
}
