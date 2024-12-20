package com.example.meet_doctor.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meet_doctor.R
import com.example.meet_doctor.adapter.adapterDoctor
import com.example.meet_doctor.Model.Doctor
import org.json.JSONObject


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

        doctorList = ArrayList()

        recyclerDoctor = view.findViewById(R.id.recyclerDoctor)

        adapterDoctor = adapterDoctor(requireContext(), doctorList)

        recyclerDoctor.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerDoctor.adapter = adapterDoctor

//        fetchDoctors()

        return view
    }

//    private fun fetchDoctors() {
//        AndroidNetworking.get("http://127.0.0.1:8000/api/")
//            .setPriority(Priority.LOW)
//            .build()
//            .getAsJSONObject(object : JSONObjectRequestListener {
//                override fun onResponse(response: JSONObject) {
//                    val doctorsArray = response.getJSONArray("doctors")
//                    val doctorsList = ArrayList<Doctor>()
//
//                    for (i in 0 until doctorsArray.length()) {
//                        val doctorObject = doctorsArray.getJSONObject(i)
//                        val id = doctorObject.getInt("id")
//                        val name = doctorObject.getString("name")
//                        val image = doctorObject.getString("image")
//                        val specialist = doctorObject.getString("specialist")
//                        val doctor = Doctor(id, name, image, specialist)
//                        doctorsList.add(doctor)
//                    }
//
//                    doctorList.clear()
//                    doctorList.addAll(doctorsList)
//                    adapterDoctor.notifyDataSetChanged()
//                }
//
//                override fun onError(error: ANError) {
//                    Log.e("Error", error.toString())
//                }
//            })
//    }
}