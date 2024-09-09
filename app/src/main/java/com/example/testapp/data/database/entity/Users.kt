package com.example.testapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class Users(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo
    val login: String,

    @ColumnInfo
    val password: String,

    @ColumnInfo
    val family: String,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val lastname: String,

    @ColumnInfo
    val email: String,

    @ColumnInfo
    val blocked: Int = 0
)
