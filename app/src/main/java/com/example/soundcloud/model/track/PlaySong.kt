package com.example.soundcloud.model.track

import com.google.gson.annotations.SerializedName

data class PlaySong(
    @SerializedName("context_uri")
    var contextUri: String,
    @SerializedName("uris")
    var uris: MutableList<String>,
    @SerializedName("position_ms")
    var positionMs: Int
)