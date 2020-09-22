package com.bcaf.ivan.finalprojectandroid.Controller.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bcaf.ivan.finalprojectandroid.Adapter.BusListAdapter
import com.bcaf.ivan.finalprojectandroid.Entity.Bus
import com.bcaf.ivan.finalprojectandroid.Helper.SessionManager
import com.bcaf.ivan.finalprojectandroid.R
import com.bcaf.ivan.finalprojectandroid.Util.BusUtil
import kotlinx.android.synthetic.main.fragment_bus.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [BusFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BusFragment : Fragment() {

    var rv_bus: RecyclerView? = null
    lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val myView: View = inflater.inflate(R.layout.fragment_bus, container, false)
        sessionManager = SessionManager(context!!)
        rv_bus = myView.findViewById(R.id.rv_bus)
        rv_bus!!.setHasFixedSize(true)
        rv_bus!!.layoutManager = LinearLayoutManager(context)

        getAllBus()

        return myView
    }

    fun getAllBus() {
        Log.i("test", "getAllBus: ${sessionManager.getSession().agencyId}")
        val agencyBody: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            sessionManager.getSession().agencyId
        )

        BusUtil().getBus().getAllBus(agencyBody).enqueue(object : Callback<List<Bus>> {
            override fun onFailure(call: Call<List<Bus>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Bus>>, response: Response<List<Bus>>) {
                var busList = response.body()!!

                createAdapter(busList)
            }
        })
    }

    fun createAdapter(busList: List<Bus>) {
        val adapter = BusListAdapter(busList)
        adapter.notifyDataSetChanged()
        rv_bus!!.adapter = adapter
    }
}