package com.example.soundcloud.model


import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class SearchSound(
    @SerializedName("playlists")
    @Expose
    var playList: PlayList
)
