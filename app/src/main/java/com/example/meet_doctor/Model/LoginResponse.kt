package com.example.meet_doctor.Model


data class LoginResponse(
    val success: Boolean,
    val message: String,
    val user: User?,
    val token: String?
)


