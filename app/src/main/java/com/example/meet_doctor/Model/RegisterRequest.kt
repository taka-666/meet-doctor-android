package com.example.meet_doctor.Model

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)

