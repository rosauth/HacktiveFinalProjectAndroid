package com.bcaf.ivan.finalprojectandroid.Controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bcaf.ivan.finalprojectandroid.Entity.Agency
import com.bcaf.ivan.finalprojectandroid.Entity.User
import com.bcaf.ivan.finalprojectandroid.Helper.CustomActivity
import com.bcaf.ivan.finalprojectandroid.Helper.FieldChecker
import com.bcaf.ivan.finalprojectandroid.Helper.ToastMessage
import com.bcaf.ivan.finalprojectandroid.R
import com.bcaf.ivan.finalprojectandroid.Util.AgencyUtil
import com.bcaf.ivan.finalprojectandroid.Util.UserUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {
    var gson: Gson = GsonBuilder().create()
    private lateinit var fieldChecker: FieldChecker
    private lateinit var message: ToastMessage
    private lateinit var activity:CustomActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        message = ToastMessage(applicationContext)
        fieldChecker = FieldChecker()
        activity=CustomActivity(this)
    }

    fun registerClick(v: View) {
        val pass = inp_password.text.toString()
        val rePass = inp_rePassword.text.toString()
        val agencyName = inp_agencyName.text.toString()
        val agencyDetails = inp_agencyDetail.text.toString()

        var user = User(
            firstName = inp_firstName.text.toString(),
            lastName = inp_lastName.text.toString(),
            password = inp_password.text.toString(),
            email = inp_email.text.toString(),
            mobileNumber = inp_mobileNumber.text.toString()
        )
        if (fieldChecker.fieldNull(
                user.firstName!!.trim(),
                user.lastName!!.trim(),
                user.password!!.trim(),
                user.email!!.trim(),
                user.mobileNumber!!.trim(),
                agencyName.trim(),
                agencyDetails.trim()
            )
        ) {
            message.nullField()
        } else {
            if (fieldChecker.checkPassword(pass, rePass)) {
                if (fieldChecker.emailValidation(user.email!!)) {
                    checkEmailRegistered(user)
                } else {
                    message.emailNotValid()
                }
            } else {
                message.passwordNotMatch()
            }
        }
    }

    fun createUser(user: User) {
        UserUtil().getUser().register(user)
            .enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    message.error()
                }

                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {
                    val userRegisterResult = response.body()!!

                    val agencyName = inp_agencyName.text.toString()
                    val agencyDetails = inp_agencyDetail.text.toString()
                    var agency: Agency =
                        Agency(
                            name = agencyName,
                            details = agencyDetails,
                            userId = userRegisterResult.id!!
                        )
                    Log.d("Agency", gson.toJson(agency))
                    createAgency(agency)
                }
            })
    }

    fun createAgency(agency: Agency) {
        AgencyUtil().getAgency().createAgency(agency)
            .enqueue(object : Callback<Agency> {
                override fun onFailure(
                    call: Call<Agency>,
                    t: Throwable
                ) {
                    message.error()
                }

                override fun onResponse(
                    call: Call<Agency>,
                    response: Response<Agency>
                ) {
                    message.custom("Register success!")
                    fieldChecker.clearField(
                        inp_firstName,
                        inp_lastName,
                        inp_email,
                        inp_mobileNumber,
                        inp_password,
                        inp_rePassword,
                        inp_agencyName,
                        inp_agencyDetail
                    )
                    activity.start(LoginActivity::class.java)
                }

            })
    }

    fun checkEmailRegistered(user: User) {
        UserUtil().getUser().checkEmail(user)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    message.error()
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val checkEmailResult = response.body()!!.string()
                    var userCheckEmailResult =
                        gson.fromJson(checkEmailResult, User::class.java)
                    if (userCheckEmailResult.id == null || userCheckEmailResult.id == "") {
                        createUser(user)
                    } else {
                        message.custom("Email is already registered!")
                    }
                }
            })
    }
}