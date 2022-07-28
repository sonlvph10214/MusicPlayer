package com.example.soundcloud.model


import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class SearchAlbum(
    @SerializedName("albums")
    @Expose
    var album: AlbumId
)
