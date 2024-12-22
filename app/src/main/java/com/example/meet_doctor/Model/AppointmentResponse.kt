package com.example.meet_doctor.Model

data class AppointmentResponse(
    val success: Boolean,
    val data: AppointmentData
)

data class AppointmentData(
    val appointment_id: Int,
    val appointment: AppointmentDetails
)

data class AppointmentDetails(
    val doctor_id: Int,
    val user_id: Int,
    val consultation_id: Int,
    val date: String,
    val time: String,
    val status: Int,
    val id: Int
)


