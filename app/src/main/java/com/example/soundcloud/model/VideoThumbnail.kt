package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VideoThumbnail(
    @SerializedName("url")
    @Expose
    var url : String,
)
