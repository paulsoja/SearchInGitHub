package com.paulsojaoutlook.searchingithub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by p-sha on 06.10.2017.
 */

public class GitHubUser {

    @SerializedName("login")
    private String userLogin;
    @SerializedName("name")
    private String userName;
    @SerializedName("followers")
    private String userFollowers;
    @SerializedName("following")
    private String userFollowing;
    @SerializedName("avatar_url")
    private String userAvatar;
    @SerializedName("email")
    private String userEmail;
    @SerializedName("public_repos")
    private String userRepos;

    public GitHubUser(String userLogin, String userName, String userFollowers, String userFollowing, String userAvatar, String userEmail) {
        this.userLogin = userLogin;
        this.userName = userName;
        this.userFollowers = userFollowers;
        this.userFollowing = userFollowing;
        this.userAvatar = userAvatar;
        this.userEmail = userEmail;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFollowers() {
        return userFollowers;
    }

    public void setUserFollowers(String userFollowers) {
        this.userFollowers = userFollowers;
    }

    public String getUserFollowing() {
        return userFollowing;
    }

    public void setUserFollowing(String userFollowing) {
        this.userFollowing = userFollowing;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRepos() {
        return userRepos;
    }

    public void setUserRepos(String userRepos) {
        this.userRepos = userRepos;
    }
}
