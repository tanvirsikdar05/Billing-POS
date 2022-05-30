package com.kaiser.kaiserinvoice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface api_response {

    @GET("login2.php")
    Call<String> sendLoginRequest(@Query("u") String str, @Query("p") String str2);
}
