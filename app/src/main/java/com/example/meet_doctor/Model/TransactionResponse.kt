package com.example.meet_doctor.Model

data class TransactionResponse(
    val success: Boolean,
    val data: List<Transaction>
)
