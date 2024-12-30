package com.example.meet_doctor.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meet_doctor.Interface.ApiService
import com.example.meet_doctor.Model.TransactionRes
import com.example.meet_doctor.R
import com.example.meet_doctor.Utilitas.ApiClient
import com.example.meet_doctor.Utilitas.TokenManager
import com.example.meet_doctor.adapter.AdapterAppointments
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterAppointments
    private val combinedList = mutableListOf<Any>()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)

        // Setup RecyclerView
        recyclerView = view.findViewById(R.id.recyclerTransaction)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = AdapterAppointments(combinedList)
        recyclerView.adapter = adapter

        fetchTransactions()

        return view

    }
    private fun fetchTransactions() {
        val token = TokenManager.getToken(requireContext())
        if (token.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Token not found, please login again", Toast.LENGTH_SHORT).show()
            return
        }
        val apiService = ApiClient.getRetrofitWithToken(token).create(ApiService::class.java)

        apiService.getTransactionDetails("Bearer $token").enqueue(object :
            Callback<TransactionRes> {
            override fun onResponse(call: Call<TransactionRes>, response: Response<TransactionRes>) {
                if (response.isSuccessful) {
                    response.body()?.data?.let { transactions ->
                        combinedList.addAll(transactions)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<TransactionRes>, t: Throwable) {
                Log.e("API", "Error fetching transactions: ${t.message}")
            }
        })
    }

}