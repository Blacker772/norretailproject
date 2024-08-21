package com.example.testapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject


@Entity(tableName = "channels")
data class Channels(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val shortName: String,

    @ColumnInfo
    val description: String
)
