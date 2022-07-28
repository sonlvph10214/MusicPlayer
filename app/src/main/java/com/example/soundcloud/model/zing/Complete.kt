package com.example.soundcloud.model.zing

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Complete(  @SerializedName("result")
                      @Expose
                      var result: Boolean,
                      @SerializedName("data")
                      var dataList :List<Data>? = null
)
