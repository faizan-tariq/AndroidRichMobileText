package com.mobile.emojis.network;

import com.mobile.emojis.model.Data;

import java.util.List;

import retrofit2.Response;

public class ServerResponse {

    private int requestCode;
    private Response<List<Data>> responseObject;

    public ServerResponse(final int requestCode, final Response<List<Data>> responseObject) {
        this.requestCode = requestCode;
        this.responseObject = responseObject;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(final int requestCode) {
        this.requestCode = requestCode;
    }

    public Response<List<Data>> getResponseObject() {
        return responseObject;
    }
}
