package com.example.macbook.libprac;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by macbook on 2017. 1. 26..
 */
public interface GitApiInterface {
    @GET("users/{user_id}/repos")
    Call<List<Repo>> listRepos(@Path("user_id") String userId);
}
