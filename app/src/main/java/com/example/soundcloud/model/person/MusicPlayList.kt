package com.example.soundcloud.model.person

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class MusicPlayList (@ColumnInfo(name = "ref") var ref: ArrayList<PlayListPerson> = ArrayList())
