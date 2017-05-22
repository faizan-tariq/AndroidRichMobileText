package com.mobile.emojis.network;

/**
 * ServerConnectionListener.
 */
public interface ServerConnectionListener {



    void onPreExecute();

    /**
     * onSuccess.
     * @param response
     */
    void onSuccess(final ServerResponse response);

    /**
     * onFailure.
     * @param requestCode
     */
    void onFailure(final int requestCode);
}
