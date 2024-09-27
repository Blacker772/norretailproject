package com.example.testapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapp.data.database.entity.Clients
import com.example.testapp.data.database.entity.SaveUser
import com.example.testapp.data.database.entity.Users
import kotlinx.coroutines.flow.Flow


@Dao
interface DAO {

    //Сохранение логина и пароля при входе
    @Insert
    suspend fun saveUser(user: SaveUser)

    //Добавление пользователя в БД
    @Insert
    suspend fun insertUser(account: Users)

    //Получение пользователя по логину для авторизации
    @Query("SELECT * FROM auth_data WHERE login = :login")
    suspend fun getUserByLoginAuth(login: String): SaveUser?

    //Добавление клиентов в БД
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertClients(clients: List<Clients>)

    //Получение всех клиентов из БД
    @Query("SELECT * FROM clients")
    fun getClients(): Flow<List<Clients>>
}