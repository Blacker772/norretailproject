package com.example.testapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapp.data.database.entity.AuthData
import com.example.testapp.data.database.entity.Channels
import com.example.testapp.data.database.entity.Users

@Database(
    entities = [Users::class, Channels::class, AuthData::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase: RoomDatabase(){

    abstract fun getDao(): DAO

    companion object{
        const val DB_NAME = "nor-retail.db"
    }
}