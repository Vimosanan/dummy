package com.example.cartrack.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//class Geo {
//    @SerializedName("lat")
//    @Expose
//    var lat: String? = null
//
//    @SerializedName("lng")
//    @Expose
//    var lng: String? = null
//
//}
data class Geo (
    var lat: String,
    var lng: String
)
