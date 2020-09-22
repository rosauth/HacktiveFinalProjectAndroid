package com.bcaf.ivan.finalprojectandroid.Services

import com.bcaf.ivan.finalprojectandroid.Entity.Agency
import com.bcaf.ivan.finalprojectandroid.Entity.Bus
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface BusService {
    @Multipart
    @POST("bus/getAllBusByAgencyId")
    fun getAllBus(@Part("agencyId") agencyId: RequestBody): Call<List<Bus>>

    @Multipart
    @POST("bus/getBusById")
    fun getBus(@Part("busId") busId:RequestBody):Call<Bus>
}