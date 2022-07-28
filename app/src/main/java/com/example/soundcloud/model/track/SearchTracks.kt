package com.example.soundcloud.model.track


import com.example.soundcloud.model.Tracks
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class SearchTracks(
    @SerializedName("tracks")
    @Expose
    var tracks: Tracks
)
