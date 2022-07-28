package com.example.soundcloud.model.zing

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Song( @SerializedName("thumb")
                 @Expose
                 var thumb : String,
                 @SerializedName("artist")
                 @Expose
                 var artist : String,
                 @SerializedName("artistIds")
                 @Expose
                 var artistIds : String,
                 @SerializedName("duration")
                 @Expose
                 var duration : String,
                 @SerializedName("block")
                 @Expose
                 var block : String,
                 @SerializedName("id")
                 @Expose
                 var id : String,
                 @SerializedName("hasVideo")
                 @Expose
                 var hasVideo : String,
                 @SerializedName("streamingStatus")
                 @Expose
                 var streamingStatus : String,
                 @SerializedName("thumbVideo")
                 @Expose
                 var thumbVideo : String,
                 @SerializedName("genreIds")
                 @Expose
                 var genreIds : String,
                 @SerializedName("disable_platform_web")
                 @Expose
                 var disable_platform_web : String,
                 @SerializedName("disSPlatform")
                 @Expose
                 var disSPlatform : String,
                 @SerializedName("is_official")
                 @Expose
                 var is_official : String,
                 @SerializedName("radioPid")
                 @Expose
                 var radioPid : String,
                 @SerializedName("zing_choice")
                 @Expose
                 var zing_choice : String,
                 @SerializedName("name")
                 @Expose
                 var name : String,
                 @SerializedName("disDPlatform")
                 @Expose
                 var disDPlatform : String


)
