package com.example.soundcloud.dao

import androidx.room.TypeConverter
import com.example.soundcloud.model.person.Music
import com.google.gson.reflect.TypeToken

import com.google.gson.Gson
import java.lang.reflect.Type


class PlayListTypeConverter {
    @TypeConverter
    fun fromMusicList(musicList: ArrayList<Music?>?): String? {
        if (musicList == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(musicList)
    }

    @TypeConverter
    fun toMusicList(musicListString: String?): ArrayList<Music>? {
        if (musicListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<Music?>?>() {}.type
        return gson.fromJson<ArrayList<Music>>(musicListString, type)
    }
}