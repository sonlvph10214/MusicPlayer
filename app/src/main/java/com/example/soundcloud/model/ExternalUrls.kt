package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExternalUrls(
    @SerializedName("spotify")
    @Expose
     var spotify : String
): Serializable
