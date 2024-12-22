package com.example.meet_doctor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meet_doctor.Model.Transaction
import com.example.meet_doctor.Model.booking
import com.example.meet_doctor.R

class adapterAppointment(
    private val appointments: List<booking>,
    private val transactions: List<Transaction>
) : RecyclerView.Adapter<adapterAppointment.AppointmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        val transaction = transactions.getOrNull(position) // Safe access to avoid out-of-bounds error

        // Bind Appointment Data
        holder.tvDoctorName.text = "Doctor ID: ${appointment.doctor_id}" // Customize as needed
        holder.tvPatientName.text = "User ID: ${appointment.user_id}" // Customize as needed
        holder.tvConsultation.text = "Consultation ID: ${appointment.consultation_id}" // Customize as needed
        holder.tvDate.text = "Date: ${appointment.date}"
        holder.tvTime.text = "Time: ${appointment.time}"
        holder.tvStatus.text = "Status: ${appointment.status}"

        // Bind Transaction Data (if available)
        transaction?.let {
            holder.tvFeeDoctor.text = "Doctor Fee: ${it.fee_doctor}"
            holder.tvFeeSpecialist.text = "Specialist Fee: ${it.fee_specialist}"
            holder.tvFeeHospital.text = "Hospital Fee: ${it.fee_hospital}"
            holder.tvSubtotal.text = "Subtotal: ${it.sub_total}"
            holder.tvVat.text = "VAT: ${it.vat}"
            holder.tvGranTotal.text = "Grand Total: ${it.total}"
        }
    }

    override fun getItemCount(): Int = appointments.size

    class AppointmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDoctorName: TextView = view.findViewById(R.id.tvDoctorName)
        val tvPatientName: TextView = view.findViewById(R.id.tvPatientName)
        val tvConsultation: TextView = view.findViewById(R.id.tvConsultation)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val tvFeeDoctor: TextView = view.findViewById(R.id.tvFeeDoctor)
        val tvFeeSpecialist: TextView = view.findViewById(R.id.tvFeeSpecialist)
        val tvFeeHospital: TextView = view.findViewById(R.id.tvFeeHospital)
        val tvSubtotal: TextView = view.findViewById(R.id.tvSubtotal)
        val tvVat: TextView = view.findViewById(R.id.tvVat)
        val tvGranTotal: TextView = view.findViewById(R.id.tvGranTotal)
    }
}
