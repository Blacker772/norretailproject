package com.example.testapp.repository

import com.example.testapp.data.auth.AuthModel
import com.example.testapp.data.auth.UserModel
import com.example.testapp.data.createuser.CreateUserModel
import com.example.testapp.data.createuser.ErrorCreateUser
import com.example.testapp.data.database.DAO
import com.example.testapp.data.database.entity.SaveUser
import com.example.testapp.data.database.entity.Users
import com.example.testapp.data.response.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val dao: DAO,
) {

    //Запрос на вход по логину и паролю
    //LoginFragment
    fun getLoginRepo(login: String, password: String): Flow<UserModel> = flow {
        val result = apiService.getLogin(AuthModel(login, password))
        if (result.isSuccessful) {
            result.body()?.let { emit(it) } ?: throw Exception("Response is null")
        } else {
            throw Exception("Error ${result.code()}")
        }
    }

    //Запрос на регистрацию
    //SignUpFragment
    fun createUSerRepo(user: CreateUserModel): Flow<ErrorCreateUser> = flow {
        try {
            val result = apiService.createUser(user)
            if (result.isSuccessful) {
                result.body()?.let { emit(it) } ?: throw Exception("Response is null")
            } else {
                val errorBody = result.errorBody()?.string() ?: "Unknown error"
                emit(ErrorCreateUser(errorBody))
            }
        } catch (e: Exception) {
            emit(ErrorCreateUser(e.message ?: "Error"))
        }

    }

    //Проверка почты(имеется ли такая почта на сервере)
    //MailFragment
    fun cheMail(mail: String): Flow<CreateUserModel> = flow {
        val result = apiService.checkMail(mail)
        if (result.isSuccessful) {
            result.body()?.let { emit(it) } ?: throw Exception("Response is null")
        } else {
            throw Exception("${result.code()}")
        }
    }

    //Метод для сохранения пользователя в БД
    //LoginFragment
    suspend fun saveUser(user: SaveUser) {
        dao.saveUser(user)
    }

    //Метод для добавления пользователя в БД
    //SignUpFragment
    suspend fun insertUser(account: Users) {
        dao.insertUser(account)
    }

    //Метод для получения пользователя из БД
    //LoginFragment
    suspend fun getAuthData(login: String): SaveUser {
        return dao.getAuthData(login)
    }
//
//    //Метод для получения пользователя по логину
//    //LoginFragment
//    suspend fun getUserByLogin(login: String): Users {
//        return dao.getUserByLogin(login)
//    }
}