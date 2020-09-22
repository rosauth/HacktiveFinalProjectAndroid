package com.bcaf.ivan.finalprojectandroid.Services

import com.bcaf.ivan.finalprojectandroid.Entity.User
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface UserService {
    @Multipart
    @POST("user/login")
    fun login(@Part("email") email: RequestBody, @Part("password") password: RequestBody): Call<String>

    @POST("user/createUser")
    fun register(@Body userInp:User):Call<User>

    @POST("user/checkEmailUserByUser")
    fun checkEmail(@Body userParam:User):Call<ResponseBody>

    @POST("user/updateUser")
    fun updateProfile(@Body user:User):Call<User>

    @Multipart
    @POST("user/getUserById")
    fun getUser(@Part("userId") userId:RequestBody):Call<User>
}
