package com.example.androidapis;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholder {
    @GET("posts")
    Call<List<Posts>> getPosts();
}


