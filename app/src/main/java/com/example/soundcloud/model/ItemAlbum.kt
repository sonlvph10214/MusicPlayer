package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ItemAlbum(
    @SerializedName("album_type")
    @Expose
    var albumType : String,
    @SerializedName("artists")
    @Expose
    var artists : List<Artist>,
    @SerializedName("href")
    @Expose
    var href : String,

    @SerializedName("id")
    @Expose
    var id : String,
    @SerializedName("images")
    @Expose
    var images : List<Images>,
    @SerializedName("name")
    @Expose
    var name : String,
    @SerializedName("release_date")
    @Expose
    var releaseDate : String

) : Serializable
