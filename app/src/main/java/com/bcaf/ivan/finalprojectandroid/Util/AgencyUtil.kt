package com.bcaf.ivan.finalprojectandroid.Util

import com.bcaf.ivan.finalprojectandroid.Helper.UtilHelper
import com.bcaf.ivan.finalprojectandroid.Services.AgencyService
import com.bcaf.ivan.finalprojectandroid.Services.ApiService
import com.bcaf.ivan.finalprojectandroid.Services.UserService

class AgencyUtil: UtilHelper {
    constructor()
    fun getAgency(): AgencyService {
        return ApiService.getClient().create(AgencyService::class.java)
    }
}