package com.example.meet_doctor.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.meet_doctor.AppointmentActivity
import com.example.meet_doctor.R
import com.example.meet_doctor.Model.Doctor
import com.squareup.picasso.Picasso

class adapterDoctor(val context: Context, val doctor: ArrayList<Doctor>) : RecyclerView.Adapter<adapterDoctor.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_doctor, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val doctor = doctor[position]

        holder.doctorName.text = doctor.name
        holder.doctorSpecialist.text = doctor.specialist

        // load image
        Picasso.get()
            .load(doctor.image)
            .into(holder.doctorImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, AppointmentActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return doctor.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val doctorName: TextView = itemView.findViewById(R.id.txtNameDoctor)
        val doctorSpecialist: TextView = itemView.findViewById(R.id.txtNameSpecialist)
        val doctorImage: ImageView = itemView.findViewById(R.id.photoDoctorImageView)
    }
}