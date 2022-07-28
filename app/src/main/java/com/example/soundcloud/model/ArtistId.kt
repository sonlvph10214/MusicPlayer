package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArtistId(
        @SerializedName("items")
        @Expose
        var items : List<ItemArtist>,
        @SerializedName("limit")
        @Expose
        var limit:Int,
        @SerializedName("next")
        @Expose
        var next:String,
        @SerializedName("offset")
        @Expose
        var offset: Int,
        @SerializedName("previous")
        @Expose
        var previous : String,
        @SerializedName("total")
        @Expose
        var total:String

)

