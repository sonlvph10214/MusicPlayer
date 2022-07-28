package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Album(
        @SerializedName("album_type")
        @Expose
        var albumType : String,
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
