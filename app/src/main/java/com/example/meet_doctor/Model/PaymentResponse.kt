package com.example.meet_doctor.Model

data class PaymentResponse(
    val success: Boolean,
    val appointment: Appointment,
    val payment_info: PaymentInfo
) {
    data class Appointment(
        val id: Int,
        val date: String,
        val time: String,
        val status: String,
        val doctor: Doctor,
        val consultation: Consultation
    ) {
        data class Doctor(
            val id: Int,
            val name: String,
            val photo: String,
            val fee: String,
            val specialist: Specialist
        ) {
            data class Specialist(
                val name: String,
                val price: String
            )
        }

        data class Consultation(
            val id: Int,
            val name: String
        )
    }

    data class PaymentInfo(
        val biaya_konsultasi: String,
        val doctor_fee: String,
        val hospital_fee: String,
        val vat: Int,
        val grand_total: Int
    )
}
