package com.example.soundcloud.model.zing

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArtistZing( @SerializedName("aliasName")
                       @Expose
                       var aliasName : String,
                       @SerializedName("thumb")
                       @Expose
                       var thumb : String,
                       @SerializedName("name")
                       @Expose
                       var name : String,
                       @SerializedName("block")
                       @Expose
                       var block : String,
                       @SerializedName("id")
                       @Expose
                       var id : String,
                       @SerializedName("oaId")
                       @Expose
                       var oaId : String
)
