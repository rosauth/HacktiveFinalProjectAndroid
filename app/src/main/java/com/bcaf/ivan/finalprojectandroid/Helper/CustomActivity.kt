@file:Suppress("DEPRECATION")

package com.bcaf.ivan.finalprojectandroid.Helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.util.Log

class CustomActivity(var activity: Activity) {
    fun startAndDestroy(route:Class<out Any>,duration:Long=100L,key:String="",value:String=""){
        val handler = Handler()
        handler.postDelayed({
            activity.startActivity(
                Intent(
                    activity.applicationContext,
                    route
                ).putExtra(key,value)
            )
            activity.finish()
        }, duration)
    }
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
    fun start(route:Class<out Any>,duration:Long=100L,key:String="",value:String=""){
        val handler = Handler()
        handler.postDelayed({
            activity.startActivity(
                Intent(
                    activity.applicationContext,
                    route
                ).putExtra(key,value)
            )
        }, duration)
    }
}