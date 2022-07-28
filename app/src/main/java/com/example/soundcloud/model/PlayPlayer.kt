package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlayPlayer(
    @SerializedName("context_uri")
    @Expose
     var contextUri : String,
    @SerializedName("uris")
    @Expose
     var uris : List<String>,
//    @SerializedName("offset")
//    @Expose
//     var offset:Offset,
    @SerializedName("position_ms")
    @Expose
     var positionMs:Int,

)
