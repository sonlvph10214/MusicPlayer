package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ExternalUrlsOwner(
    @SerializedName("spotify")
    @Expose
     var spotify : String
)
