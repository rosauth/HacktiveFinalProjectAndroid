package com.bcaf.ivan.finalprojectandroid.Controller.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.bcaf.ivan.finalprojectandroid.Entity.User
import com.bcaf.ivan.finalprojectandroid.Helper.FieldChecker
import com.bcaf.ivan.finalprojectandroid.Helper.SessionManager
import com.bcaf.ivan.finalprojectandroid.Helper.ToastMessage
import com.bcaf.ivan.finalprojectandroid.R
import com.bcaf.ivan.finalprojectandroid.Util.UserUtil
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    lateinit var sessionManager:SessionManager
    lateinit var toast:ToastMessage
    lateinit var fieldChecker: FieldChecker
    lateinit var userUtil: UserUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager= SessionManager(context!!)
        toast= ToastMessage(context!!)
        fieldChecker= FieldChecker()
        userUtil= UserUtil()
        getProfile()
    }
    fun createTextChangeListener(){
        inp_profile_password.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().trim()!=""){
                    til_profile_repassword.visibility=ViewGroup.VISIBLE
                }else{
                    til_profile_repassword.visibility=ViewGroup.GONE
                }
            }

        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    fun getProfile(){
        val userId: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            sessionManager.getSession().userId
        )
        UserUtil().getUser().getUser(userId).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                var user = response.body()
                txt_profile_name.text="${user!!.firstName} ${user!!.lastName}"
                inp_profile_firstName.setText(user!!.firstName)
                inp_profile_lastName.setText(user!!.lastName)
                inp_profile_mobileNumber.setText(user!!.mobileNumber)
                inp_profile_email.setText(user!!.email)

                createTextChangeListener()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                toast.error()
            }

        })
    }
}