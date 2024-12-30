package com.example.meet_doctor.Interface

import com.example.meet_doctor.Model.AppointmentRequest
import com.example.meet_doctor.Model.AppointmentRes
import com.example.meet_doctor.Model.AppointmentResponse
import com.example.meet_doctor.Model.CallbackResponse
import com.example.meet_doctor.Model.Doctor
import com.example.meet_doctor.Model.DoctorResponse
import com.example.meet_doctor.Model.LoginRequest
import com.example.meet_doctor.Model.LoginResponse
import com.example.meet_doctor.Model.PaymentRequest
import com.example.meet_doctor.Model.PaymentResponse
import com.example.meet_doctor.Model.PaymentStoreResponse
import com.example.meet_doctor.Model.User
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

import com.example.meet_doctor.Model.RegisterRequest
import com.example.meet_doctor.Model.RegisterResponse
import com.example.meet_doctor.Model.TransactionRes
import com.example.meet_doctor.Model.TransactionResponse
import com.example.meet_doctor.Model.bookingResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface  ApiService {
    // Endpoint register
    @POST("register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    // Endpoint login
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("home")
    fun getDoctor(): Call<DoctorResponse>

    // Create Appointment
    @POST("appointment")
    fun createAppointment(
        @Body appointmentRequest: AppointmentRequest
    ): Call<AppointmentResponse>
    // Get Payment Details
    @GET("payment/appointment/{id}")
    fun getPaymentDetails(@Path("id") id: Int): Call<PaymentResponse>
    // Create Payment
    @POST("payment")
    fun createPayment(
        @Body appointmentId: Map<String, Int> // Mengirimkan appointment_id sebagai body
    ): Call<TransactionResponse>

    @GET("appointment")
    fun getAppointments(
        @Header("Authorization") token: String
    ): Call<AppointmentRes>

    @GET("backsite/transaction")
    fun getTransactionDetails(@Header("Authorization")token: String): Call<TransactionRes>

    @GET("user")
    fun getUser(): Call<User>

}


