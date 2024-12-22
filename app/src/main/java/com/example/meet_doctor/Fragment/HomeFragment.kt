package com.example.meet_doctor.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meet_doctor.AppointmentActivity
import com.example.meet_doctor.Interface.ApiService
import com.example.meet_doctor.Model.Doctor
import com.example.meet_doctor.Model.DoctorResponse
import com.example.meet_doctor.R
import com.example.meet_doctor.Utilitas.ApiClient
import com.example.meet_doctor.Utilitas.TokenManager
import com.example.meet_doctor.adapter.adapterDoctor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var recyclerDoctor: RecyclerView
    private lateinit var doctorList: ArrayList<Doctor>
    private lateinit var adapterDoctor: adapterDoctor

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inisialisasi daftar dokter dan RecyclerView
        doctorList = ArrayList()
        recyclerDoctor = view.findViewById(R.id.recyclerDoctor)

        // Buat adapter dengan lambda listener
        adapterDoctor = adapterDoctor(requireContext(), doctorList) { doctor ->
            // Navigasi ke AppointmentActivity
            val intent = Intent(requireContext(), AppointmentActivity::class.java)
            intent.putExtra("DOCTOR_ID", doctor.id)
            intent.putExtra("DOCTOR_NAME", doctor.name)
            intent.putExtra("DOCTOR_IMAGE", doctor.image)
            intent.putExtra("DOCTOR_SPECIALIST", doctor.specialists)
            startActivity(intent)
        }

        recyclerDoctor.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerDoctor.adapter = adapterDoctor

        // Fetch data dokter
        fetchDoctors()

        return view
    }

    private fun fetchDoctors() {
        // Ambil token dari TokenManager
        val token = TokenManager.getToken(requireContext())
        if (token.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Token not found, please login again", Toast.LENGTH_SHORT).show()
            return
        }

        // Konfigurasikan API Service dengan token
        val apiService = ApiClient.getRetrofitWithToken(token).create(ApiService::class.java)

        apiService.getDoctor().enqueue(object : Callback<DoctorResponse> {
            override fun onResponse(call: Call<DoctorResponse>, response: Response<DoctorResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val doctorResponse = response.body()!!
                    doctorList.clear()
                    doctorList.addAll(doctorResponse.doctor)
                    adapterDoctor.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch doctors", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DoctorResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
