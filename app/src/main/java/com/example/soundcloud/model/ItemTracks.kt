package com.example.soundcloud.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ItemTracks(
    @SerializedName("added_at")
    @Expose
     var addedAt : String,
    @SerializedName("added_by")
    @Expose
     var addedBy : AddedBy,
    @SerializedName("is_local")
    @Expose
     var isLocal : Boolean,
    @SerializedName("primary_color")
    @Expose
     var primaryColor : String,
    @SerializedName("track")
    @Expose
     var track : Track,
    @SerializedName("video_thumbnail")
    @Expose
     var videoThumbnail : VideoThumbnail


) : Serializable
