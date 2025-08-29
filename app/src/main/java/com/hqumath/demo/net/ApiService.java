package com.hqumath.demo.net;


import com.hqumath.demo.bean.BaseResultEntity;
import com.hqumath.demo.bean.CommitEntity;
import com.hqumath.demo.bean.ReposEntity;
import com.hqumath.demo.bean.UserInfoEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * ****************************************************************
 * 文件名称: MainService
 * 作    者: Created by gyd
 * 创建时间: 2019/1/22 17:11
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public interface ApiService {

    //@Headers("Content-Type: application/json")
    //@POST("api/auth/loginApp")
    //Observable<BaseResult<LoginBean>> login(@Body Map<String, String> body);

    //获取设备列表
    //@GET("api/device/list")
    //Observable<BaseResult<DeviceListBean>> getDeviceList(@QueryMap Map<String, String> query);

    //获取用户信息
    @GET("users/{userName}")
    Observable<UserInfoEntity> getUserInfo(@Path("userName") String userName);

    //获取用户仓库
    @GET("users/{userName}/repos")
    Observable<List<ReposEntity>> getMyRepos(@Path("userName") String userName, @Query("per_page") int per_page, @Query("page") long page);

    //获取星标仓库
    @GET("users/{userName}/starred")
    Observable<List<ReposEntity>> getStarred(@Path("userName") String userName, @Query("per_page") int per_page, @Query("page") long page);

    //获取被追随
    @GET("users/{userName}/followers")
    Observable<List<UserInfoEntity>> getFollowers(@Path("userName") String userName, @Query("per_page") int per_page, @Query("page") long page);

    //获取仓库信息
    @GET("repos/{userName}/{reposName}")
    Observable<ReposEntity> getReposInfo(@Path("userName") String userName,
                                         @Path("reposName") String reposName);

    //获取仓库提交记录 分页
    @GET("repos/{userName}/{reposName}/commits?sha=master")
    Observable<List<CommitEntity>> getCommits(@Path("userName") String userName,
                                              @Path("reposName") String reposName,
                                              @Query("per_page") int per_page, @Query("page") long page);

    //文件下载
    @Streaming/*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Observable<ResponseBody> download(@Url String url);

    //文件上传 模拟
    @Multipart
    @POST("api/user/update")
    Observable<BaseResultEntity> upload(@Part MultipartBody.Part part);

}
