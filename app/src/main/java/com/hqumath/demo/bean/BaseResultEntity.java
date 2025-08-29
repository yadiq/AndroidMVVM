package com.hqumath.demo.bean;

/**
 * 网络请求统一返回格式
 */
public class BaseResultEntity<T> {
    //判断标示
    private String type;
    //提示信息
    private String resultMsg;
    //错误码
    private String resultCode;
    //显示数据（用户需要关心的数据）
    private T data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
