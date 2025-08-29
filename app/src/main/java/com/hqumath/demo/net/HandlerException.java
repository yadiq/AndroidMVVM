package com.hqumath.demo.net;

import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.hqumath.demo.utils.LogUtil;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * 处理错误信息
 */

public class HandlerException {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, ERROR.HTTP_ERROR + "");
            LogUtil.e("OkHttp", "错误号：" + httpException.code(), false);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                case GATEWAY_TIMEOUT:
                default:
                    ex.setMessage("网络错误(" + httpException.code() + ")");
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponseThrowable(resultException, resultException.code + "");
            ex.setMessage(resultException.message);
            return ex;
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            ex = new ResponseThrowable(e, ERROR.PARSE_ERROR + "");
            ex.setMessage("解析错误");
            return ex;
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            ex = new ResponseThrowable(e, ERROR.NETWORD_ERROR + "");
            ex.setMessage("网络连接失败");
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseThrowable(e, ERROR.SSL_ERROR + "");
            ex.setMessage("证书验证失败");
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ResponseThrowable(e, ERROR.SSL_ERROR + "");
            ex.setMessage("连接超时");
            return ex;
        } else if (e instanceof ResponseThrowable) {
            return (ResponseThrowable) e;
        } else {
            ex = new ResponseThrowable(e, ERROR.UNKNOWN + "");
            if (TextUtils.isEmpty(e.getMessage())) {
                ex.message = "未知错误...";
            } else {
                ex.message = e.getMessage();
            }
            return ex;
        }
    }

    /**
     * 约定异常
     */
    class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;
    }

    public static class ResponseThrowable extends RuntimeException {
        public String code;
        public String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ResponseThrowable(Throwable throwable, String code) {
            super(throwable);
            this.code = code;

        }

        public ResponseThrowable(String message) {
            super(message);
            this.message = message;
        }

        public ResponseThrowable(String message, String code) {
            super(message);
            this.message = message;
            this.code = code;
        }

    }

    public class ServerException extends RuntimeException {
        public int code;
        public String message;
    }
}
