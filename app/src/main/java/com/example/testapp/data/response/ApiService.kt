package com.example.testapp.data.response
import com.example.testapp.data.auth.AuthModel
import com.example.testapp.data.createuser.CreateUserModel
import com.example.testapp.data.auth.UserModel
import com.example.testapp.data.channels.ChannelsModel
import com.example.testapp.data.createuser.ErrorCreateUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    //каналы сбыта
    @GET("/channels")
    suspend fun getChannels(): Response<List<ChannelsModel>>

    //запрос на вход по логину и паролю
    @POST("/login")
    suspend fun getLogin(@Body auth: AuthModel): Response<UserModel>

    //регистрация пользователя
    @POST("/users")
    suspend fun createUser(@Body user: CreateUserModel): Response<ErrorCreateUser>

    //проверка почты(имеется ли такая почта на сервере)
    @GET("/user/find/email")
    suspend fun checkMail(mail: String): Response<CreateUserModel>

}