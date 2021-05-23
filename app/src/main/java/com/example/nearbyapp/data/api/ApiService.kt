package com.example.nearbyapp.data.api

import com.example.nearbyapp.data.models.Places
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("search")
    suspend fun getPlaces(
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("v") v: String,
        @Query("ll") ll: String,
        @Query("radius") radius: String,
    ): Places

}