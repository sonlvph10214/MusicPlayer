package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("display_name")
    @Expose
    var displayName : String,
    @SerializedName("href")
    @Expose
    var href : String,
    @SerializedName("id")
    @Expose
    var id : String,
    @SerializedName("images")
    @Expose
    var images : List<Images>,
    @SerializedName("type")
    @Expose
    var type : String,
    @SerializedName("uri")
    @Expose
    var uri : String,


)
