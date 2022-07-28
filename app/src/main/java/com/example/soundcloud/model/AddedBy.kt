package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddedBy(
    @SerializedName("external_urls")
    @Expose
    var external_urls : ExternalUrls,
    @SerializedName("href")
    @Expose
    var href : String,
    @SerializedName("id")
    @Expose
    var id : String,
    @SerializedName("type")
    @Expose
    var type : String,
    @SerializedName("uri")
    @Expose
    var uri : String,


)
