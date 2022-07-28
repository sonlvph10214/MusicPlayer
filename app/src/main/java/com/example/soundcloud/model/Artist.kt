package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Artist(
        @SerializedName("external_urls")
        @Expose
        var externalUrls : ExternalUrls,
        @SerializedName("href")
        @Expose
        var href : String,
        @SerializedName("id")
        @Expose
        var id : String,
        @SerializedName("name")
        @Expose
        var name : String,
        @SerializedName("type")
        @Expose
        var type : String,
        @SerializedName("uri")
        @Expose
        var uri : String,

)
