package com.hqumath.demo.net;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 生成请求体
 */
public class CreateRequestBodyUtil {
    /**
     * 根据Json串生成请求体
     *
     * @param json
     * @return
     */
    public static RequestBody createRequestBody(String json) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
    }

    /**
     * 根据Json串生成请求体
     *
     * @return
     */
    public static RequestBody createRequestBody(HashMap<String, Object> map) {
        Gson gson = new Gson();
        String body = gson.toJson(map);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), body);
    }

    /**
     * 根据Json串生成请求体
     *
     * @return
     */
    public static RequestBody createRequestBody(Object bean) {
        Gson gson = new Gson();
        String body = gson.toJson(bean);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), body);
    }

    /**
     * 根据File生成请求体
     *
     * @return
     */
    public static MultipartBody.Part createRequestBody(String key, File file) {
        RequestBody requestFile = RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(key, key, requestFile);//(key, file.getName(), requestFile)
        return body;
    }
}

