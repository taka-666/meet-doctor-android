package com.example.meet_doctor.Model


data class DoctorResponse(
    val status: Boolean,
    val message: String,
    val doctor: List<Doctor>
)
