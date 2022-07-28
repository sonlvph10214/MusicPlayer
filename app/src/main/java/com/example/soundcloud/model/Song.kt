package com.example.soundcloud.model

import java.io.Serializable

data class Song(var name:String ,
                var raw:Int,
                var img:Int,
                var singer:String,
                var duration:Long
) : Serializable


