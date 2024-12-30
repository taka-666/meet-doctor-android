package com.example.meet_doctor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meet_doctor.Model.Appointment
import com.example.meet_doctor.Model.Transaction
import com.example.meet_doctor.R

class AdapterAppointments(
    private val combinedList: List<Any> // Gabungan dari Appointment dan Transaction
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_APPOINTMENT = 1
        private const val TYPE_TRANSACTION = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (combinedList[position]) {
            is Appointment -> TYPE_APPOINTMENT
            is Transaction -> TYPE_TRANSACTION
            else -> throw IllegalArgumentException("Unknown item type at position $position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_APPOINTMENT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_appointment, parent, false)
                AppointmentViewHolder(view)
            }
            TYPE_TRANSACTION -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_transaction, parent, false)
                TransactionViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AppointmentViewHolder -> holder.bind(combinedList[position] as Appointment)
            is TransactionViewHolder -> holder.bind(combinedList[position] as Transaction)
        }
    }

    override fun getItemCount(): Int = combinedList.size

    // ViewHolder untuk Appointment
    inner class AppointmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvDoctorName: TextView = view.findViewById(R.id.tvDoctorName)
        private val tvPatientName: TextView = view.findViewById(R.id.tvPatientName)
        private val tvConsultation: TextView = view.findViewById(R.id.tvConsultation)
        private val tvDate: TextView = view.findViewById(R.id.tvDate)
        private val tvTime: TextView = view.findViewById(R.id.tvTime)
        private val tvStatus: TextView = view.findViewById(R.id.tvStatus)

        fun bind(appointment: Appointment) {
            tvDoctorName.text = appointment.doctor.name
            tvPatientName.text = appointment.user.name
            tvConsultation.text = appointment.consultation.name
            tvDate.text = appointment.date
            tvTime.text = appointment.time
            tvStatus.text = when (appointment.status) {
                "1" -> "Payment Completed"
                "2" -> "Waiting Payment"
                else -> "Unknown Status"
            }
        }
    }

    // ViewHolder untuk Transaction
    inner class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvDoctorName: TextView = view.findViewById(R.id.tvDoctorName)
        private val tvPatientName: TextView = view.findViewById(R.id.tvPatientName)
        private val tvFeeDoctor: TextView = view.findViewById(R.id.tvFeeDoctor)
        private val tvFeeSpecialist: TextView = view.findViewById(R.id.tvFeeSpecialist)
        private val tvFeeHospital: TextView = view.findViewById(R.id.tvFeeHospital)
        private val tvSubtotal: TextView = view.findViewById(R.id.tvSubtotal)
        private val tvVat: TextView = view.findViewById(R.id.tvVat)
        private val tvGrandTotal: TextView = view.findViewById(R.id.tvGranTotal)

        fun bind(transaction: Transaction) {
            tvDoctorName.text = transaction.doctor.name
            tvPatientName.text = transaction.user.name
            tvFeeDoctor.text = "IDR ${transaction.fee_doctor}"
            tvFeeSpecialist.text = "IDR ${transaction.fee_specialist}"
            tvFeeHospital.text = "IDR ${transaction.fee_hospital}"
            tvSubtotal.text = "IDR ${transaction.sub_total}"
            tvVat.text = "IDR ${transaction.vat}"
            tvGrandTotal.text = "IDR ${transaction.grand_total}"
        }
    }
}
