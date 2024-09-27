package com.example.testapp.repository

import com.example.testapp.data.models.auth.AuthModel
import com.example.testapp.data.models.createuser.CreateUserModel
import com.example.testapp.data.database.DAO
import com.example.testapp.data.database.entity.Clients
import com.example.testapp.data.database.entity.SaveUser
import com.example.testapp.data.database.entity.Users
import com.example.testapp.data.response.ApiService
import com.example.testapp.ui.log_in.UiStateLogIn
import com.example.testapp.ui.recover.mail.UiStateMail
import com.example.testapp.ui.sign_up.UiStateSignUp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val dao: DAO,
) {

    //API
    //Запрос на вход по логину и паролю
    //LoginFragment
    fun authorizationRepo(user: AuthModel): Flow<UiStateLogIn> = flow {
        try {
            val result = apiService.authorization(user)
            if (result.isSuccessful) {
                val requestBody = result.body()
                if (requestBody != null) {
                    emit(UiStateLogIn.Data(requestBody))
                } else {
                    throw Exception("response body is null")
                }
            } else {
                val errorBody = result.errorBody()?.string() ?: "unknown error"
                emit(UiStateLogIn.Error(errorBody))
            }
        } catch (e: Exception) {
            emit(UiStateLogIn.Error(e.message))
        }
    }

    //БД
    //Получение пользователя по логину для авторизации(есть ли такой пользователь в БД)
    //LoginFragment
    suspend fun getUserByLoginAuth(login: String): SaveUser? {
        return dao.getUserByLoginAuth(login)
    }

    //БД
    //Метод для сохранения логина и пароля при входе(если нет такого пользователя в БД, то сохраняем)
    //LoginFragment
    suspend fun saveUserAuth(user: SaveUser) {
        dao.saveUser(user)
    }

    //API
    //Регистрация пользователя на сервере и в БД
    //SignUpFragment
    fun createUSerRepo(account: CreateUserModel): Flow<UiStateSignUp> = flow {
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
                    emit(UiStateSignUp.Data(responseBody))
                } else {
                    throw Exception("response body is null")
                }
            } else {
                val errorBody = result.errorBody()?.string() ?: "unknown error"
                emit(UiStateSignUp.Error(errorBody))
            }
        } catch (e: Exception) {
            emit(UiStateSignUp.Error(e.message))
        }
    }

    //API
    //Проверка почты(имеется ли такая почта на сервере)
    //MailFragment
    fun checkMailRepo(mail: String): Flow<UiStateMail> = flow {
        try {
            val result = apiService.checkMail(mail)
            if (result.isSuccessful) {
                val requestBody = result.body()
                if (requestBody != null) {
                    emit(UiStateMail.Data(requestBody))
                } else {
                    throw Exception("response body is null")
                }
            } else {
                val errorBody = result.errorBody()?.string() ?: "unknown error"
                emit(UiStateMail.Error(errorBody))
            }
        } catch (e: Exception) {
            emit(UiStateMail.Error(e.message))
        }
    }


    //API
    //Получение клиентов из сервера и добавление в БД
    //RouteFragment
    suspend fun insertClientsInDBRepo() {
        try {
            val result = apiService.getClients()
            if (result.isSuccessful) {
                result.body()?.let {
                    dao.insertClients(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //БД
    //Получение клиентов из БД
    //RouteFragment
    fun getClientsDBRepo(): Flow<List<Clients>> {
        return dao.getClients()
    }
}