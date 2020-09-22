package com.bcaf.ivan.finalprojectandroid.Helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.auth0.android.jwt.JWT
import com.bcaf.ivan.finalprojectandroid.Entity.TokenResult
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.apache.commons.codec.binary.Base64
import org.json.JSONObject


class SessionManager {
    val gson: Gson = GsonBuilder().create()
    var prefs: SharedPreferences

    constructor(context: Context) {
        prefs = context.getSharedPreferences("TokenUser", Context.MODE_PRIVATE)
    }

    // endregion
    fun setSession(rs: String) {
        if(rs!="error") {
            val jwt = JWT(rs)
            val getClaims = jwt.claims
            val token = getClaims["iss"]!!.asString()
            prefs
            with(prefs.edit()) {
                try {
                    putString(
                        "Token",
                        gson.toJson(gson.fromJson(token, TokenResult::class.java))
                    )
                } catch (e: Exception) {
                    putString(
                        "Token",
                        gson.toJson(TokenResult())
                    )
                }
                commit()
            }
        }else{
            prefs
            with(prefs.edit()) {
                try {
                    putString(
                        "Token",
                        gson.toJson(TokenResult())
                    )
                } catch (e: Exception) {
                    putString(
                        "Token",
                        gson.toJson(TokenResult())
                    )
                }
                commit()
            }
        }
    }

    fun getSession(): TokenResult {
        return gson.fromJson(
            if (prefs.getString("Token", "") == "") gson.toJson(
                TokenResult()
            ) else prefs.getString("Token", ""), TokenResult::class.java
        )
    }

    @SuppressLint("CommitPrefEdits")
    fun removeSession() {
        prefs.edit().remove("Token").apply()
    }
}