package com.example.meet_doctor.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.meet_doctor.R
import org.json.JSONObject


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        doctorList = ArrayList()

        recyclerDoctor = view.findViewById(R.id.recyclerDoctor)

        adapterDoctor = AdapterDoctor(requireContext(), doctorList)

        recyclerDoctor.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerDoctor.adapter = adapterDoctor

        fetchDoctors()

        return view
    }

    private fun fetchDoctors() {
        AndroidNetworking.get("http://172.20.10.6:8000/api/")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val doctorsArray = response.getJSONArray("doctors")
                    val doctorsList = ArrayList<Doctor>()

                    for (i in 0 until doctorsArray.length()) {
                        val doctorObject = doctorsArray.getJSONObject(i)
                        val id = doctorObject.getInt("id")
                        val name = doctorObject.getString("name")
                        val image = doctorObject.getString("image")
                        val specialistObject = doctorObject.getJSONObject("specialist")
                        val specialistName = specialistObject.getString("name")
                        val specialist = Specialist(specialistName)
                        val doctor = Doctor(id, name, image, specialist)
                        doctorsList.add(doctor)
                    }

                    doctorList.clear()
                    doctorList.addAll(doctorsList)
                    adapterDoctor.notifyDataSetChanged()
                }

                override fun onError(error: ANError) {
                    Log.e("Error", error.toString())
                }
            })
    }
}