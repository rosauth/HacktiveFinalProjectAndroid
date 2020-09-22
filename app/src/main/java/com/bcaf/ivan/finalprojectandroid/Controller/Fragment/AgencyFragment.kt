package com.bcaf.ivan.finalprojectandroid.Controller.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bcaf.ivan.finalprojectandroid.Entity.Agency
import com.bcaf.ivan.finalprojectandroid.Entity.User
import com.bcaf.ivan.finalprojectandroid.Helper.SessionManager
import com.bcaf.ivan.finalprojectandroid.Helper.ToastMessage
import com.bcaf.ivan.finalprojectandroid.R
import com.bcaf.ivan.finalprojectandroid.Util.AgencyUtil
import com.bcaf.ivan.finalprojectandroid.Util.UserUtil
import kotlinx.android.synthetic.main.fragment_agency.*
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [AgencyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AgencyFragment : Fragment() {
    lateinit var sessionManager: SessionManager
    lateinit var toast: ToastMessage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager= SessionManager(context!!)
        toast= ToastMessage(context!!)

        getAgency()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agency, container, false)
    }
    fun getAgency(){
        val agencyId: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            sessionManager.getSession().agencyId
        )
        AgencyUtil().getAgency().getAgency(agencyId).enqueue(object : Callback<Agency> {
            override fun onResponse(call: Call<Agency>, response: Response<Agency>) {
                var agency = response.body()
                txt_agency_name.text="${agency!!.name}"
                txt_agency_details.text="${agency!!.details}"
                inp_agency_name.setText(agency.name)
                inp_agency_details.setText(agency.details)
            }

            override fun onFailure(call: Call<Agency>, t: Throwable) {
                toast.error()
            }

        })
    }


}