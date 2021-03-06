package com.example.cartrack.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//class Address {
//    @SerializedName("street")
//    @Expose
//    var street: String? = null
//
//    @SerializedName("suite")
//    @Expose
//    var suite: String? = null
//
//    @SerializedName("city")
//    @Expose
//    var city: String? = null
//
//    @SerializedName("zipcode")
//    @Expose
//    var zipcode: String? = null
//
//    @SerializedName("geo")
//    @Expose
//    var geo: Geo? = null
//
//
//}
class Address (
    var street: String,
    var suite: String,
    var city: String,
    var zipcode: String,
    var geo: Geo
)