package com.example.soundcloud.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.soundcloud.model.person.MusicPlayList
import com.example.soundcloud.model.person.PlayListPerson

@Dao
interface PlayListDao {
    @Query("SELECT*FROM playlistPerson")
    fun getAll(): MutableList<PlayListPerson>

    @Insert
    fun insertAll(vararg playlistPerson: PlayListPerson)


}