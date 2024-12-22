package com.example.meet_doctor.Model

data class booking(
    val id: Int,
    val user_id: Int,
    val doctor_id: Int,
    val consultation_id: Int,
    val date: String,
    val time: String,
    val status: String
)
