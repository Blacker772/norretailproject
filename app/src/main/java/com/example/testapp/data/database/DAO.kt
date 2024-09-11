package com.example.testapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.testapp.data.database.entity.SaveUser
import com.example.testapp.data.database.entity.Users


@Dao
interface DAO {

    //Сохранение логина и пароля при входе
    @Insert
    suspend fun saveUser(user: SaveUser)

    //Добавление пользователя в БД
    @Insert
    suspend fun insertUser(account: Users)

    //Получение пользователя по логину
    @Query("SELECT * FROM users WHERE login = :login")
    suspend fun getUserByLogin(login: String): Users?
}