package com.example.meet_doctor.Model

data class AppointmentRequest(
    val doctor_id: Int,
    val consultation_topic: String,
    val date: String,
    val time: String
)
