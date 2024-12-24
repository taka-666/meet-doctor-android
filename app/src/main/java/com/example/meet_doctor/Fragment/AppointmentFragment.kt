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
import com.example.meet_doctor.Model.Transaction
import com.example.meet_doctor.Model.TransactionResponse
import com.example.meet_doctor.Model.booking
import com.example.meet_doctor.Model.bookingResponse
import com.example.meet_doctor.R
import com.example.meet_doctor.adapter.adapterAppointment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppointmentFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: adapterAppointment
    private var appointments: List<booking> = listOf()
    private var transactions: List<Transaction> = listOf()

    private val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://f8d7-2001-448a-4046-26f4-6058-21a7-e0a3-3514.ngrok-free.app/backsite/") // Ganti dengan base URL Anda
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_appointment, container, false)

        recyclerView = view.findViewById(R.id.recyclerAppointment)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

//        fetchAppointments()
//        fetchTransactions()

        return view
    }

//    private fun fetchAppointments() {
//        apiService.getAppointments().enqueue(object : Callback<bookingResponse> {
//            override fun onResponse(call: Call<bookingResponse>, response: Response<bookingResponse>) {
//                if (response.isSuccessful) {
//                    appointments = response.body()?.data ?: listOf()
//                    updateAdapter()
//                } else {
//                    Log.e("API Error", "Failed to fetch appointments: ${response.errorBody()?.string()}")
//                }
//            }
//
//            override fun onFailure(call: Call<bookingResponse>, t: Throwable) {
//                Log.e("API Error", "Error fetching appointments: ${t.message}")
//            }
//        })
//    }
//
//    private fun fetchTransactions() {
//        apiService.getTransactions().enqueue(object : Callback<TransactionResponse> {
//            override fun onResponse(call: Call<TransactionResponse>, response: Response<TransactionResponse>) {
//                if (response.isSuccessful) {
//                    transactions = response.body()?.data ?: listOf()
//                    updateAdapter()
//                } else {
//                    Log.e("API Error", "Failed to fetch transactions: ${response.errorBody()?.string()}")
//                }
//            }
//
//            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
//                Log.e("API Error", "Error fetching transactions: ${t.message}")
//            }
//        })
//    }
//
//    private fun updateAdapter() {
//        if (::recyclerView.isInitialized) {
//            adapter = adapterAppointment(appointments, transactions)
//            recyclerView.adapter = adapter
//        }
//    }
}
