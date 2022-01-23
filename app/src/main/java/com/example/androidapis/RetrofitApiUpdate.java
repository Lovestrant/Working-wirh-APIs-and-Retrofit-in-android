package com.example.androidapis;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface RetrofitApiUpdate {
    @PUT("api/users/2")
    //method to put data
    Call <DataModal> updateData(@Body DataModal dataModal);

}
