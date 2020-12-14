package com.example.miis200.util;

public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}

