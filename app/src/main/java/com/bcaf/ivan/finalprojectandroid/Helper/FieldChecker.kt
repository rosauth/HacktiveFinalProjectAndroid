package com.bcaf.ivan.finalprojectandroid.Helper

import android.text.Editable
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

class FieldChecker {
    fun emailValidation(email: String): Boolean {
        var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
    fun fieldNull(vararg text: String): Boolean {
        return text.filter(fun(it: String): Boolean {
            return it == ""
        }).isNotEmpty()
    }

    fun fieldNull(vararg text: TextInputEditText): Boolean {
        return text.filter(fun(it: TextInputEditText): Boolean {
            return it.text.toString() == ""
        }).isNotEmpty()
    }
    fun checkPassword(pass: String, rePass: String): Boolean {
        return pass == rePass
    }
    fun clearField(vararg field:EditText){
        field.forEach { it.text.clear() }
    }
}