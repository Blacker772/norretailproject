package com.example.testapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.testapp.data.database.entity.SaveUser
import com.example.testapp.data.database.entity.Users


@Dao
interface DAO {

//    //получение пользователя
//    @Query("SELECT * FROM auth_data WHERE login = :login")
//    suspend fun getUser(login: String): SaveUser?

    //добавление пользователя, если он отсутствует в БД
    @Insert
    suspend fun saveUser(user: SaveUser)

    //добавление пользователя
    @Insert
    suspend fun insertUser(account: Users)

    //получение пользователя по логину
    @Query("SELECT * FROM users WHERE login = :login")
    suspend fun getUserByLogin(login: String): Users?
}