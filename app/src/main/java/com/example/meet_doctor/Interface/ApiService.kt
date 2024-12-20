package com.example.meet_doctor.Interface

import com.example.meet_doctor.Model.Doctor
import com.example.meet_doctor.Model.LoginRequest
import com.example.meet_doctor.Model.LoginResponse
import com.example.meet_doctor.Model.User
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

import com.example.meet_doctor.Model.RegisterRequest
import com.example.meet_doctor.Model.RegisterResponse
import retrofit2.Call


interface ApiService {
    // Endpoint register
    @POST("register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    // Endpoint login
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}


