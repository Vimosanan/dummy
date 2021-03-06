package com.example.cartrack.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//class Company {
//    @SerializedName("name")
//    @Expose
//    var name: String? = null
//
//    @SerializedName("catchPhrase")
//    @Expose
//    var catchPhrase: String? = null
//
//    @SerializedName("bs")
//    @Expose
//    var bs: String? = null
//
//}

data class Company (
    var name: String,
    var catchPhrase: String,
    var bs: String? = null
)