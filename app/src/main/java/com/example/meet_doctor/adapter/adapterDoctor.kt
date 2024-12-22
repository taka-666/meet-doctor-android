package com.example.meet_doctor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meet_doctor.Model.Doctor
import com.example.meet_doctor.R

class adapterDoctor(
    private val context: Context,
    private val doctorList: List<Doctor>,
    private val onDoctorClick: (Doctor) -> Unit
) : RecyclerView.Adapter<adapterDoctor.DoctorViewHolder>() {

    inner class DoctorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val doctorName: TextView = view.findViewById(R.id.tvDoctorName)
        val doctorImage: ImageView = view.findViewById(R.id.ivDoctorImage)
        val doctorSpecialists: TextView = view.findViewById(R.id.tvDoctorSpecialist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctorList[position]

        // Set data ke View
        holder.doctorName.text = doctor.name
        holder.doctorSpecialists.text = doctor.specialists
        Glide.with(context)
            .load(doctor.image)
            .placeholder(R.drawable.doctor_1)
            .into(holder.doctorImage)

        // Tambahkan listener untuk klik pada ImageView
        holder.doctorImage.setOnClickListener {
            onDoctorClick(doctor) // Panggil listener
        }
    }

    override fun getItemCount(): Int = doctorList.size
}

