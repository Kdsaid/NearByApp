package com.example.nearbyapp.data.models

data class Venue(
    val categories: List<Category>,
    val hasPerk: Boolean,
    val id: String,
    val location: Location,
    val name: String,
    val referralId: String,
    val venuePage: VenuePage
)