package com.example.testapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testapp.data.database.entity.AuthData
import com.example.testapp.data.database.entity.Users


@Dao
interface DAO {

    @Insert
    suspend fun authRemember(data: AuthData)

    @Insert
    suspend fun insertUser(data: Users)

    @Query("SELECT * FROM users WHERE login = :login")
    suspend fun getUserByLogin(login: String): Users

}