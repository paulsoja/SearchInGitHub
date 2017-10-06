package com.paulsojaoutlook.searchingithub.rest;

import com.paulsojaoutlook.searchingithub.model.GitHubUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by p-sha on 06.10.2017.
 */

public interface GitHubUserCall {

    @GET("/users/{user}")
    Call<GitHubUser> getUser(@Path("user")String user);
}
