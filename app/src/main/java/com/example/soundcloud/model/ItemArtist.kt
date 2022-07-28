package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ItemArtist(
    @SerializedName("followers")
    @Expose
    var followers : Followers,
//    @SerializedName("genres")
//    @Expose
//    var genres : Genres,
    @SerializedName("id")
    @Expose
    var id : String,

    @SerializedName("images")
    @Expose
    var images : List<Images>,
    @SerializedName("name")
    @Expose
    var name : String,

) : Serializable
