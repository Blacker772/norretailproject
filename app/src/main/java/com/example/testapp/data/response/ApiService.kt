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
import retrofit2.http.Query

interface ApiService {

    //Каналы сбыта
    @GET("/channels")
    suspend fun getChannels(): Response<List<ChannelsModel>>

    //Вход по логину и паролю
    @POST("/login")
    suspend fun getLogin(@Body auth: AuthModel): Response<UserModel>

    //Регистрация пользователя
    @POST("/users")
    suspend fun createUser(@Body user: CreateUserModel): Response<ErrorCreateUser>

    //Проверка почты(имеется ли такая почта на сервере)
    @GET("/user/find/email")
    suspend fun checkMail(@Query("email") mail: String): Response<CreateUserModel>
}