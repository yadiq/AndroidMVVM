package com.hqumath.demo.net;

public interface HttpListener {
    void onSuccess(Object object);

    void onError(String errorMsg, String code);
}
