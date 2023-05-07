package com.example.hlc06_enriquefernandez.apiservice;

import com.example.hlc06_enriquefernandez.BuildConfig;
import com.example.hlc06_enriquefernandez.modelo.*;

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

    /// <summary>
    /// Obtiene una lista de gatos, con un límite de señalado en la llamada
    /// </summary>
    @Headers("x-api-key: "+ BuildConfig.API_KEY)
    @GET("v1/images/search")
    Call<List<Cat>> getCats(@Query("limit") int limit);

    /// <summary>
    /// Obtiene una lista de gatos añadidos a favoritos
    /// </summary>
    @Headers("x-api-key: "+ BuildConfig.API_KEY)
    @GET("v1/favourites")
    Call<List<FavouriteCat>> getFavouriteCats();

    /// <summary>
    /// Añade un gato a la lista de favoritos
    /// @param newFavouriteCat Objeto que contiene el id del gato a añadir
    /// </summary>
    @Headers("x-api-key: "+ BuildConfig.API_KEY)
    @POST("v1/favourites")
    Call<NewFavouriteCatResponse> addToFavourites(@Body NewFavouriteCat newFavouriteCat);

    /// <summary>
    /// Elimina un gato de la lista de favoritos
    /// @param favouriteId Id del gato a eliminar
    /// </summary>
    @Headers("x-api-key: "+ BuildConfig.API_KEY)
    @DELETE("v1/favourites/{favourite_id}")
    Call<Void> removeFromFavourites(@Path("favourite_id") String favouriteId);
}
