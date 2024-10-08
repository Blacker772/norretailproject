package com.example.testapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "auth_data", indices = [Index(value = ["login"], unique = true)])
data class SaveUser(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo
    val login: String,

    @ColumnInfo
    val password: String
)
