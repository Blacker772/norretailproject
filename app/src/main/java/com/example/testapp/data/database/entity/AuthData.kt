package com.example.testapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Auth_data")
data class AuthData(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo
    val login: String,

    @ColumnInfo
    val password: String
)
