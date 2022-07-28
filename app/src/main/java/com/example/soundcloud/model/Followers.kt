package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Followers(
    @SerializedName("total")
    @Expose
    var total : Int
)
