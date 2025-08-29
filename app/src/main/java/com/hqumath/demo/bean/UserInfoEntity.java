package com.hqumath.demo.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * ****************************************************************
 * 文件名称: UserInfoEntity
 * 作    者: Created by gyd
 * 创建时间: 2019/7/17 11:48
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
//@Entity(primaryKeys = {"firstName", "lastName"}) 复合主键
//@Entity(ignoredColumns = "picture") 忽略父类字段，不持久化
@Entity(tableName = "user_info")
public class UserInfoEntity {
    /**
     * login : Ninja2005
     * id : 13248087
     * node_id : MDQ6VXNlcjEzMjQ4MDg3
     * avatar_url : https://avatars3.githubusercontent.com/u/13248087?v=4
     * gravatar_id :
     * url : https://api.github.com/users/Ninja2005
     * html_url : https://github.com/Ninja2005
     * followers_url : https://api.github.com/users/Ninja2005/followers
     * following_url : https://api.github.com/users/Ninja2005/following{/other_user}
     * gists_url : https://api.github.com/users/Ninja2005/gists{/gist_id}
     * starred_url : https://api.github.com/users/Ninja2005/starred{/owner}{/repo}
     * subscriptions_url : https://api.github.com/users/Ninja2005/subscriptions
     * organizations_url : https://api.github.com/users/Ninja2005/orgs
     * repos_url : https://api.github.com/users/Ninja2005/repos
     * events_url : https://api.github.com/users/Ninja2005/events{/privacy}
     * received_events_url : https://api.github.com/users/Ninja2005/received_events
     * type : User
     * site_admin : false
     * name : Adi
     * company : null
     * blog : https://juejin.im/user/572cb9001ea4930064cd9ac5
     * location : Hangzhou
     * email : null
     * hireable : null
     * bio : null
     * public_repos : 3
     * public_gists : 0
     * followers : 1
     * following : 7
     * created_at : 2015-07-09T03:14:55Z
     * updated_at : 2019-07-16T07:36:40Z
     */
    private String login;
    //@PrimaryKey(autoGenerate = true) 自动ID分配
    @PrimaryKey
    private int id;
    //@ColumnInfo(name = "login_name") 改名
    //@Ignore 忽略字段，不持久化
    private String node_id;
    private String avatar_url;
    private String gravatar_id;
    private String url;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private boolean site_admin;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String hireable;
    private String bio;
    private int public_repos;
    private int public_gists;
    private int followers;
    private int following;
    private String created_at;
    private String updated_at;

    // to be consistent w/ changing backend order, we need to keep a data like this
    private int indexInResponse = -1;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public void setGravatar_id(String gravatar_id) {
        this.gravatar_id = gravatar_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    public String getGists_url() {
        return gists_url;
    }

    public void setGists_url(String gists_url) {
        this.gists_url = gists_url;
    }

    public String getStarred_url() {
        return starred_url;
    }

    public void setStarred_url(String starred_url) {
        this.starred_url = starred_url;
    }

    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    public void setSubscriptions_url(String subscriptions_url) {
        this.subscriptions_url = subscriptions_url;
    }

    public String getOrganizations_url() {
        return organizations_url;
    }

    public void setOrganizations_url(String organizations_url) {
        this.organizations_url = organizations_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public void setEvents_url(String events_url) {
        this.events_url = events_url;
    }

    public String getReceived_events_url() {
        return received_events_url;
    }

    public void setReceived_events_url(String received_events_url) {
        this.received_events_url = received_events_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSite_admin() {
        return site_admin;
    }

    public void setSite_admin(boolean site_admin) {
        this.site_admin = site_admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHireable() {
        return hireable;
    }

    public void setHireable(String hireable) {
        this.hireable = hireable;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }

    public int getPublic_gists() {
        return public_gists;
    }

    public void setPublic_gists(int public_gists) {
        this.public_gists = public_gists;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getIndexInResponse() {
        return indexInResponse;
    }

    public void setIndexInResponse(int indexInResponse) {
        this.indexInResponse = indexInResponse;
    }
}
