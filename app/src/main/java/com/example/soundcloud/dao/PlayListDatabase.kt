package com.example.soundcloud.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.soundcloud.model.person.PlayListPerson
import com.example.soundcloud.view.stream.MyMusicFragment


@Database(entities = [PlayListPerson::class], version = 1)
@TypeConverters(PlayListTypeConverter::class)
abstract class PlayListDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlayListDao
}