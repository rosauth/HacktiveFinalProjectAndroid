package com.bcaf.ivan.finalprojectandroid.Services

import com.bcaf.ivan.finalprojectandroid.Entity.Agency
import com.bcaf.ivan.finalprojectandroid.Entity.User
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AgencyService {
    @POST("agency/createAgency")
    fun createAgency(@Body agencyInp: Agency): Call<Agency>

    @Multipart
    @POST("agency/getAgencyById")
    fun getAgency(@Part("agencyId") agencyId:RequestBody):Call<Agency>

    @POST("agency/updateAgency")
    fun updateAgency(@Body agency: Agency): Call<Agency>
}