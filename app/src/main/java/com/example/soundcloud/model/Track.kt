package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Track(
    @SerializedName("album")
    @Expose
    var album : Album,
    @SerializedName("artists")
    @Expose
     var artists : List<Artist>,
    @SerializedName("duration_ms")
    @Expose
     var durationMs : String,
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

) : Serializable
