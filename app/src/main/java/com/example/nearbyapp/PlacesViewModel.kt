package com.example.nearbyapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.nearbyapp.data.api.ApiHelper
import com.example.nearbyapp.data.api.RetrofitBuilder
import com.example.nearbyapp.data.repository.MainRepository
import com.example.nearbyapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class PlacesViewModel : ViewModel() {

    private val repository = MainRepository(ApiHelper(RetrofitBuilder.apiService))

    fun getPlaces(
        client_id: String,
        client_secret: String,
        v: String,
        ll: String,radius:String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(repository.getPlaces(client_id, client_secret, v, ll,radius)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}