package com.sachin.fintrack.utils;

public interface ResponseCallback {

    void onResponse (String response);

    void onError (Throwable throwable);
}
