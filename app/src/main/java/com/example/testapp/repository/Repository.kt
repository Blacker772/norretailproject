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
    private val dao: DAO
) {

    //API
    //Запрос на вход по логину и паролю
    //LoginFragment
    fun getLoginRepo(user: AuthModel): Flow<UserModel> = flow {
        try {
            val result = apiService.getLogin(user)
            if (result.isSuccessful) {
                val requestBody = result.body()
                if (requestBody != null) {
                    emit(requestBody)
                } else {
                    throw Exception("Response body is null")
                }
            } else {
                throw Exception("Error ${result.code()}: ${result.message()}")
            }
        } catch (e: Exception) {
            throw Exception(e.message ?: "An unknown error occurred")
        }
    }

    //БД
    //Метод для сохранения логина и пароля при входе
    //LoginFragment
    suspend fun saveUserAuth(user: SaveUser) {
        dao.saveUser(user)
    }

    //API
    //Регистрация пользователя на сервере и в БД
    //SignUpFragment
    fun createUSerRepo(account: CreateUserModel): Flow<ErrorCreateUser> = flow {
        try {
            val result = apiService.createUser(account)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) {
                    dao.insertUser(
                        Users(
                            null, account.login,
                            account.password, account.family,
                            account.name, account.lastname, account.email
                        )
                    )
                    emit(responseBody)
                } else {
                    throw Exception("Response body is null")
                }
            } else {
                val errorBody = result.errorBody()?.string() ?: "Unknown error"
                emit(ErrorCreateUser(errorBody))
            }
        } catch (e: Exception) {
            emit(ErrorCreateUser(e.message ?: "An unknown error occurred"))
        }
    }

    //API
    //Проверка почты(имеется ли такая почта на сервере)
    //MailFragment
    fun checkMailRepo(mail: String): Flow<CreateUserModel> = flow {
        try {
            val result = apiService.checkMail(mail)
            if (result.isSuccessful) {
                val requestBody = result.body()
                if (requestBody != null) {
                    emit(requestBody)
                } else {
                    throw Exception("Response body is null")
                }
            } else {
                throw Exception("Error ${result.code()}: ${result.message()}")
            }
        } catch (e: Exception) {
            throw Exception(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getUserByLoginAuth(login: String): SaveUser? {
        return dao.getUserByLoginAuth(login)
    }
}