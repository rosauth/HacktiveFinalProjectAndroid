package com.bcaf.ivan.finalprojectandroid.Util

import com.bcaf.ivan.finalprojectandroid.Helper.UtilHelper
import com.bcaf.ivan.finalprojectandroid.Services.AgencyService
import com.bcaf.ivan.finalprojectandroid.Services.ApiService
import com.bcaf.ivan.finalprojectandroid.Services.BusService

class BusUtil: UtilHelper {
    constructor()
    fun getBus(): BusService {
        return ApiService.getClient().create(BusService::class.java)
    }
}