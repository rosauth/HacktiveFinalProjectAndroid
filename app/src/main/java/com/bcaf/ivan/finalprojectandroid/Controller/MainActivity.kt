package com.bcaf.ivan.finalprojectandroid.Controller

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.bcaf.ivan.finalprojectandroid.Controller.Fragment.AgencyFragment
import com.bcaf.ivan.finalprojectandroid.Controller.Fragment.BusFragment
import com.bcaf.ivan.finalprojectandroid.Controller.Fragment.ProfileFragment
import com.bcaf.ivan.finalprojectandroid.Entity.Agency
import com.bcaf.ivan.finalprojectandroid.Entity.User
import com.bcaf.ivan.finalprojectandroid.Helper.CustomActivity
import com.bcaf.ivan.finalprojectandroid.Helper.FieldChecker
import com.bcaf.ivan.finalprojectandroid.Helper.SessionManager
import com.bcaf.ivan.finalprojectandroid.Helper.ToastMessage
import com.bcaf.ivan.finalprojectandroid.R
import com.bcaf.ivan.finalprojectandroid.Util.AgencyUtil
import com.bcaf.ivan.finalprojectandroid.Util.UserUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_agency.*
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val bundle = Bundle()
    private lateinit var sessionManager: SessionManager
    private lateinit var activity: CustomActivity
    private lateinit var toast: ToastMessage
    private lateinit var fieldChecker: FieldChecker
    private var gson: Gson = GsonBuilder().create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sessionManager = SessionManager(applicationContext)
        activity = CustomActivity(this)
        toast = ToastMessage(applicationContext)
        fieldChecker = FieldChecker()
        nav_bottomTab.setOnNavigationItemSelectedListener(this)
        nav_bottomTab.selectedItemId = R.id.tab_bus
    }

    override fun onResume() {
        super.onResume()
        var busFragment = BusFragment()
        busFragment.arguments = bundle
        loadfragment(busFragment)
    }

    override fun onBackPressed() {
        showDialogFun()
    }

    fun loadfragment(fragment: Fragment): Boolean {
        supportFragmentManager
            .beginTransaction().replace(R.id.cont_fragment, fragment)
            .commit()
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment?

        when (item.itemId) {
            R.id.tab_agency -> fragment = AgencyFragment()
            R.id.tab_bus -> fragment = BusFragment()
            R.id.tab_profile -> fragment = ProfileFragment()
            else -> fragment = BusFragment()
        }

        return loadfragment(fragment)
    }

    private fun showDialogFun() {
        var alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Logout?")
        alertDialogBuilder.setMessage("Your work will be automatically saved")
        alertDialogBuilder.setIcon(ResourcesCompat.getDrawable(resources,
            R.drawable.ic_question_mark, null))
        alertDialogBuilder.setPositiveButton("Yes") { _: DialogInterface, i: Int ->
            sessionManager.removeSession()
            activity.startAndDestroy(LoginActivity::class.java)
        }

        alertDialogBuilder.setNegativeButton("No") { dialog: DialogInterface, i: Int ->
            dialog.cancel()
        }

        var alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        var nButton: Button = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        nButton.setTextColor(Color.rgb(0, 165, 255))

        var pButton: Button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        pButton.setTextColor(Color.rgb(0, 165, 255))
    }

    fun onClickFunction(view: View) {
        when (view.id)
        {
            R.id.btn_logout ->showDialogFun()
            R.id.btn_agency_save->saveDataAgency()
            R.id.btn_profile_save->saveChangeProfileData()
            else->showDialogFun()
        }
    }
    fun saveDataAgency(){
        if (!fieldChecker.fieldNull(inp_agency_name, inp_agency_details)) {
            var agency = Agency()
            agency.name = inp_agency_name.text.toString()
            agency.details = inp_agency_details.text.toString()
            agency.id = sessionManager.getSession().agencyId
            AgencyUtil().getAgency().updateAgency(agency).enqueue(object : Callback<Agency> {
                override fun onFailure(call: Call<Agency>, t: Throwable) {
                    toast.error()
                }

                override fun onResponse(call: Call<Agency>, response: Response<Agency>) {
                    var agency=response.body()!!
                    txt_agency_name.text=agency.name
                    txt_agency_details.text=agency.details
                    toast.custom("Update Success!")
                }

            })
        } else {
            toast.nullField()
        }
    }
    fun saveChangeProfileData() {
        var updateUser = User()
        var boolUser = false
        var boolPassword = false
        if (!fieldChecker.fieldNull(
                inp_profile_firstName,
                inp_profile_lastName,
                inp_profile_mobileNumber,
                inp_profile_email
            )
        ) {
            if (fieldChecker.emailValidation(inp_profile_email.text.toString())) {
                updateUser.firstName = inp_profile_firstName.text.toString()
                updateUser.lastName = inp_profile_lastName.text.toString()
                updateUser.mobileNumber = inp_profile_mobileNumber.text.toString()
                updateUser.email = inp_profile_email.text.toString()
                boolUser = true
            } else {
                toast.emailNotValid()
            }
        } else {
            toast.nullField()
        }
        if (!fieldChecker.fieldNull(inp_profile_password.text.toString().trim())) {
            if (fieldChecker.checkPassword(
                    inp_profile_password.text.toString(),
                    inp_profile_repassword.text.toString()
                )
            ) {
                updateUser.password = inp_profile_password.text.toString()
                boolPassword = true
            } else {
                toast.passwordNotMatch()
            }
        } else {
            boolPassword = true
        }
        if (boolUser && boolPassword) {
            updateUser.id = sessionManager.getSession().userId
            checkEmailValidation(updateUser)
        }
    }

    fun checkEmailValidation(updateUser: User) {
        UserUtil().getUser().checkEmail(updateUser).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                toast.error()
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                val checkEmailResult = response.body()!!.string()
                var userCheckEmailResult =
                    gson.fromJson(checkEmailResult, User::class.java)
                if (userCheckEmailResult.id == null || userCheckEmailResult.id == "") {
                    updateUser(updateUser)
                } else {
                    toast.custom("Email is already registered!")
                }
            }

        })
    }

    fun updateUser(updateUser: User) {
        UserUtil().getUser().updateProfile(updateUser)
            .enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    toast.error()
                }

                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {
                    var user = response.body()!!
                    txt_profile_name.text = "${user.firstName} ${user.lastName}"
                    toast.custom("Update Success!")
                }

            })
    }
}
