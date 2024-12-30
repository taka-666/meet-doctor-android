package com.example.meet_doctor.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meet_doctor.Interface.ApiService
import com.example.meet_doctor.Model.Appointment
import com.example.meet_doctor.Model.AppointmentRes
import com.example.meet_doctor.Model.Transaction
import com.example.meet_doctor.Model.TransactionRes
import com.example.meet_doctor.R
import com.example.meet_doctor.Utilitas.ApiClient
import com.example.meet_doctor.Utilitas.TokenManager
import com.example.meet_doctor.adapter.AdapterAppointments
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterAppointments
    private val combinedList = mutableListOf<Any>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_appointment, container, false)

        // Setup RecyclerView
        recyclerView = view.findViewById(R.id.recyclerAppointment)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = AdapterAppointments(combinedList)
        recyclerView.adapter = adapter

        fetchAppointments()

        return view
    }

    private fun fetchAppointments() {
        val token = TokenManager.getToken(requireContext())
        if (token.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Token not found, please login again", Toast.LENGTH_SHORT).show()
            return
        }
        val apiService = ApiClient.getRetrofitWithToken(token).create(ApiService::class.java)

        apiService.getAppointments("Bearer $token").enqueue(object : Callback<AppointmentRes> {
            override fun onResponse(call: Call<AppointmentRes>, response: Response<AppointmentRes>) {
                if (response.isSuccessful) {
                    response.body()?.data?.let { appointments ->
                        combinedList.addAll(appointments)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<AppointmentRes>, t: Throwable) {
                Log.e("API", "Error fetching appointments: ${t.message}")
            }
        })
    }

}
