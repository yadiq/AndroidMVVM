package com.hqumath.demo.ui.follow;

import androidx.lifecycle.MutableLiveData;

import com.hqumath.demo.base.BaseViewModel;
import com.hqumath.demo.bean.UserInfoEntity;
import com.hqumath.demo.net.HttpListener;
import com.hqumath.demo.repository.MyModelOld;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ****************************************************************
 * 文件名称: ProfileDetailViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/7/17 11:52
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ProfileDetailViewModel extends BaseViewModel {

    public String userName;
    //用户信息
    public MutableLiveData<String> userInfoResultCode = new MutableLiveData<>();//0成功；other失败
    public String userInfoResultMsg;
    public String avatar_url;
    public MutableLiveData<String> location = new MutableLiveData<>();
    public MutableLiveData<String> created_at = new MutableLiveData<>();
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> company = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> blog = new MutableLiveData<>();

    public ProfileDetailViewModel() {
        mModel = new MyModelOld();
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        ((MyModelOld) mModel).getUserInfo(userName, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                UserInfoEntity data = (UserInfoEntity) object;
                avatar_url = data.getAvatar_url();
                location.postValue(data.getLocation());
                //时间格式化
                String date = data.getCreated_at();//2011-12-29T04:45:11Z
                date = date.replace("Z", " UTC");//UTC是世界标准时间
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date date1 = format1.parse(date);
                    String date2 = format2.format(date1);
                    created_at.postValue(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                name.postValue(data.getName());
                company.postValue(data.getCompany());
                email.postValue(data.getEmail());
                blog.postValue(data.getBlog());
                userInfoResultCode.postValue("0");
            }

            @Override
            public void onError(String errorMsg, String code) {
                userInfoResultMsg = errorMsg;
                userInfoResultCode.postValue(code);
            }
        });
    }

}
