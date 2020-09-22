package com.bcaf.ivan.finalprojectandroid.Controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bcaf.ivan.finalprojectandroid.Helper.CustomActivity
import com.bcaf.ivan.finalprojectandroid.Helper.SessionManager
import com.bcaf.ivan.finalprojectandroid.R

class SplashActivity : Activity() {
    lateinit var handler: Handler
    lateinit var activity: CustomActivity
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sessionManager = SessionManager(applicationContext)
        activity = CustomActivity(this)
        var logo = findViewById<ImageView>(R.id.ic_logo)
        var animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fade_in)
        logo.startAnimation(animation)
        if (activity.isOnline(applicationContext))
            if (sessionManager.getSession().userId != "")
                activity.startAndDestroy(MainActivity::class.java, 7500L)
            else
                activity.startAndDestroy(LoginActivity::class.java, 7500L)
        else
            activity.startAndDestroy(LoginActivity::class.java, 7500L)

    }
}
