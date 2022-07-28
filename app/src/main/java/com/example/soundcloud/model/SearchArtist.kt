package com.example.soundcloud.model


import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class SearchArtist(
    @SerializedName("artists")
    @Expose
    var artist: ArtistId
)
