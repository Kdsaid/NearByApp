package com.example.nearbyapp.data.repository

import com.example.nearbyapp.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getPlaces(
        client_id: String,
        client_secret: String,
        v: String,
        ll: String,
        radius: String
    ) = apiHelper.getPlaces(client_id, client_secret, v, ll,radius)

}