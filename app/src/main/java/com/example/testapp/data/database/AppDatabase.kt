package com.example.testapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapp.data.database.entity.SaveUser
import com.example.testapp.data.database.entity.Channels
import com.example.testapp.data.database.entity.Clients
import com.example.testapp.data.database.entity.Users

@Database(
    entities = [Users::class, Channels::class, SaveUser::class, Clients::class],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase: RoomDatabase(){

    abstract fun getDao(): DAO

    companion object{
        const val DB_NAME = "nor-retail.db"
    }
}