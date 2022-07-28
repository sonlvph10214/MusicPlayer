package com.example.soundcloud.model.track

import com.google.gson.annotations.SerializedName

data class PlayRequest(
    @SerializedName("device_id")
    var deviceId: String,
    var playSong: PlaySong,

    )