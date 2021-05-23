package com.example.nearbyapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.nearbyapp.configs.Constants
import com.example.nearbyapp.utils.*
import com.google.android.gms.location.*
import com.google.gson.Gson

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 999
    }
    private lateinit var viewModel: PlacesViewModel


    var rvPlaces:RecyclerView ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         rvPlaces = findViewById(R.id.rvPlaces)
        findViewById<TextView>(R.id.realtime)
            .setOnClickListener {

                when {
                    PermissionUtils.isAccessFineLocationGranted(this) -> {
                        when {
                            PermissionUtils.isLocationEnabled(this) -> {
                                setUpLocationListener()
                            }
                            else -> {
                                PermissionUtils.showGPSNotEnabledDialog(this)
                            }
                        }
                    }
                    else -> {
                        PermissionUtils.requestAccessFineLocationPermission(
                            this,
                            LOCATION_PERMISSION_REQUEST_CODE
                        )
                    }
                }
            places()

        }


        places()

    }


    private fun places(latLon:String="40.7099,-73.9622") {
        viewModel = ViewModelProvider(this).get(PlacesViewModel::class.java)
        viewModel.getPlaces(
            Constants.clientId,
            Constants.clientSecret,
            Constants.VersionInfo, latLon
        ,Constants.Radius
        )
            .observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> resource.data?.let { getPlacs ->
//                            applicationContext.toast(Gson().toJson(getPlacs))

                            rvPlaces.apply {
                                this?.adapter =
                                    ShowFlagAdapter(
                                        getPlacs.response.venues)
                            }
                        }

                        Status.ERROR -> {
                            applicationContext.toast("Something went wrong, try later")
                        }
                        Status.LOADING -> {
                        }
                    }
                }
            }

    }

    private fun setUpLocationListener() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // for getting the current location update after every 2 seconds with high accuracy
        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    for (location in locationResult.locations) {
//                        latTextView.text = location.latitude.toString()
//                        lngTextView.text = location.longitude.toString()
                        places( location.latitude.toString()+","+
                                location.longitude.toString())

                    }
                    // Few more things we can do here:
                    // For example: Update the location of user on server
                }
            },
            Looper.myLooper()
        )
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when {
                        PermissionUtils.isLocationEnabled(this) -> {
                            setUpLocationListener()
                        }
                        else -> {
                            PermissionUtils.showGPSNotEnabledDialog(this)
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.location_permission_not_granted),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}