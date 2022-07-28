package com.example.soundcloud.model.zing

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Data(  @SerializedName("song")
                  @Expose
                  var songList :List<Song>? = null,
                  @SerializedName("artist")
                  @Expose
                  var artistList :List<ArtistZing>? = null

)
