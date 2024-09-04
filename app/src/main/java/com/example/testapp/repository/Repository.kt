package com.example.testapp.repository

import com.example.testapp.data.auth.AuthModel
import com.example.testapp.data.auth.UserModel
import com.example.testapp.data.createuser.CreateUserModel
import com.example.testapp.data.createuser.ErrorCreateUser
import com.example.testapp.data.response.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService
) {
    fun getLoginRepo(login: String, password: String): Flow<UserModel> = flow {
        val result = apiService.getLogin(AuthModel(login, password))
        if (result.isSuccessful) {
            result.body()?.let { emit(it) } ?: throw Exception("Response is null")
        } else {
            throw Exception("Error ${result.code()}")
        }
    }

    fun createUSerRepo(user: CreateUserModel): Flow<ErrorCreateUser> = flow {
        val result = apiService.createUser(user)
        if (result.isSuccessful) {
            result.body()?.let { emit(it) } ?: throw Exception("Response is null")
        } else {
            throw Exception("${result.code()}")
        }
    }
}