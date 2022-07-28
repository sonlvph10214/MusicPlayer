package com.example.soundcloud.model.person

import androidx.room.*
import com.example.soundcloud.dao.PlayListTypeConverter

@Entity
data class PlayListPerson(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "playlist")
    @TypeConverters(PlayListTypeConverter::class)
    var playlist: ArrayList<Music>?,
    @ColumnInfo(name = "createdBy") var createdBy: String,
    @ColumnInfo(name = "createdOn") var createdOn: String
) {
    constructor() : this(
        "", null,
        "", ""
    )
}
