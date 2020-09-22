package com.bcaf.ivan.finalprojectandroid.Helper

import okhttp3.MediaType
import okhttp3.RequestBody

open class UtilHelper {
    fun createParam(param:String):RequestBody {
      return RequestBody.create(
            MediaType.parse("text/plain"),
            param
        )
    }
}