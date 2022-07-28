package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArtistTracks(
    @SerializedName("tracks")
    @Expose
    var tracks : List<Track>
)
