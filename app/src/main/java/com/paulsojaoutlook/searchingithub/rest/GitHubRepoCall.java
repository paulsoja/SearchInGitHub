package com.paulsojaoutlook.searchingithub.rest;

import com.paulsojaoutlook.searchingithub.model.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by p-sha on 07.10.2017.
 */

public interface GitHubRepoCall {
    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> getRepo(@Path("user")String name);
}
