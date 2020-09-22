package com.bcaf.ivan.finalprojectandroid.Helper

import android.content.Context
import android.widget.Toast
import com.bcaf.ivan.finalprojectandroid.R

class ToastMessage(val context: Context) {
    fun nullField(duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context,context.getString(R.string.field_empty) , duration).show()
    }

    fun custom(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, msg, duration).show()
    }

    fun emailNotValid(duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context,context.getString(R.string.email_not_valid) , duration).show()
    }

    fun error(duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context,context.getString(R.string.error),duration).show()
    }
    fun passwordNotMatch(duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context,context.getString(R.string.password_not_match),duration).show()
    }

}