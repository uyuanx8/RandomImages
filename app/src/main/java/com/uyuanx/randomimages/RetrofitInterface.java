package com.uyuanx.randomimages;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("https://api.unsplash.com/search/photos")
    Call<ResultList> getRandomImage(@Query("client_id") String key,
                                    @Query("query") String query,
                                    @Query("per_page") int per_page,
                                    @Query("page") int page);
}