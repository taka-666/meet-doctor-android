package com.example.meet_doctor.Model

data class AppointmentRes(
    val success: Boolean,
    val message: String,
    val data: List<Appointment>
)

data class Appointment(
    val id: Int,
    val date: String,
    val time: String,
    val status: String,
    val doctor: Doctor,
    val user: User,
    val consultation: Consultation
)

data class TransactionRes(
    val success: Boolean,
    val data: List<Transaction>
)

data class Transaction(
    val id: Int,
    val doctor: Doctor,
    val user: User,
    val appointment_id: Int,
    val transaction_code: String,
    val fee_doctor: String,
    val fee_specialist: String,
    val fee_hospital: String,
    val sub_total: String,
    val vat: String,
    val grand_total: String,
    val status: String
)

data class Consultation(
    val id: Int,
    val name: String)


