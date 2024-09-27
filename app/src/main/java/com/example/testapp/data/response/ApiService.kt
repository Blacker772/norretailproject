package com.example.testapp.data.response
import com.example.testapp.data.models.auth.AuthModel
import com.example.testapp.data.models.createuser.CreateUserModel
import com.example.testapp.data.models.auth.UserModel
import com.example.testapp.data.models.channels.ChannelsModel
import com.example.testapp.data.models.createuser.ErrorCreateUser
import com.example.testapp.data.database.entity.Clients
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    //Каналы сбыта
    @GET("/channels")
    suspend fun getChannels(): Response<List<ChannelsModel>>

    //Авторизация пользователя
    @POST("/login")
    suspend fun authorization(@Body auth: AuthModel): Response<UserModel>

    //Регистрация пользователя
    @POST("/users")
    suspend fun createUser(@Body user: CreateUserModel): Response<ErrorCreateUser>

    //Проверка почты(имеется ли такая почта на сервере)
    @GET("/user/find/email")
    suspend fun checkMail(@Query("email") mail: String): Response<CreateUserModel>

    //Получение
    @GET("/clients")
    suspend fun getClients(): Response<List<Clients>>
}