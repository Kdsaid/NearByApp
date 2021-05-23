package com.example.nearbyapp.data.api


class ApiHelper(private val apiService: ApiService) {
    suspend fun getPlaces(
        client_id: String,
        client_secret: String,
        v: String,
        ll: String,
        radius: String
    ) = apiService.getPlaces(client_id, client_secret, v, ll,radius)

}