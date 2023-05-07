package com.example.hlc06_enriquefernandez.apiservice;

import com.example.hlc06_enriquefernandez.BuildConfig;
import com.example.hlc06_enriquefernandez.modelo.Cat;
import com.example.hlc06_enriquefernandez.modelo.FavouriteCat;
import com.example.hlc06_enriquefernandez.modelo.NewFavouriteCat;
import com.example.hlc06_enriquefernandez.modelo.NewFavouriteCatResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ICatApiService {

    @Headers("x-api-key: "+ BuildConfig.API_KEY)
    @GET("v1/images/search")
    Call<List<Cat>> getCats(@Query("limit") int limit);

    @Headers("x-api-key: "+ BuildConfig.API_KEY)
    @GET("v1/favourites")
    Call<List<FavouriteCat>> getFavouriteCats();

    @Headers("x-api-key: "+ BuildConfig.API_KEY)
    @POST("v1/favourites")
    Call<NewFavouriteCatResponse> addToFavourites(@Body NewFavouriteCat newFavouriteCat);

    @Headers("x-api-key: "+ BuildConfig.API_KEY)
    @DELETE("v1/favourites/{favourite_id}")
    Call<Void> removeFromFavourites(@Path("favourite_id") String favouriteId);
}
