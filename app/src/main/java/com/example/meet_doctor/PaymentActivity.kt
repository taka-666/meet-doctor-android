package com.example.meet_doctor

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.meet_doctor.Interface.ApiService
import com.example.meet_doctor.Model.CallbackResponse
import com.example.meet_doctor.Model.PaymentResponse
import com.example.meet_doctor.Model.TransactionResponse
import com.example.meet_doctor.Utilitas.ApiClient
import com.example.meet_doctor.Utilitas.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : AppCompatActivity() {

    private lateinit var tvDoctorNameCard: TextView
    private lateinit var tvDoctorSpecialistCard: TextView
    private lateinit var ivDoctorImageCard: ImageView

    private lateinit var tvConsultation: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvStatus: TextView

    private lateinit var tvBiayaKonsultasi: TextView
    private lateinit var tvDoctorFee: TextView
    private lateinit var tvHospitalFee: TextView
    private lateinit var tvVat: TextView
    private lateinit var tvGrandTotal: TextView
    private lateinit var btnPayNow: Button

    private var appointmentId: Int = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Doctor Info view
        tvDoctorNameCard = findViewById(R.id.tvDoctorNameCard)
        tvDoctorSpecialistCard = findViewById(R.id.tvDoctorSpecialistCard)
        ivDoctorImageCard = findViewById(R.id.photoProfile)

        // Inisialisasi View
        tvConsultation = findViewById(R.id.tvConsultation)
        tvDate = findViewById(R.id.tvDate)
        tvTime = findViewById(R.id.tvTime)
        tvStatus = findViewById(R.id.tvStatus)

        tvBiayaKonsultasi = findViewById(R.id.tvBiayaKonsultasi)
        tvDoctorFee = findViewById(R.id.tvFeeDoctor)
        tvHospitalFee = findViewById(R.id.tvFeeHospital)
        tvVat = findViewById(R.id.tvVat)
        tvGrandTotal = findViewById(R.id.tvGranTotal)
        btnPayNow = findViewById(R.id.btnPaynow)

        // Ambil appointment_id dari SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        appointmentId = sharedPreferences.getInt("APPOINTMENT_ID", -1)

        if (appointmentId == -1) {
            Toast.makeText(this, "No valid appointment found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Fetch data appointment dan payment details
        fetchPaymentDetails(appointmentId)

        // Set tombol Pay Now
        btnPayNow.setOnClickListener {
            createPayment(appointmentId)
        }
    }

    private fun fetchPaymentDetails(appointmentId: Int) {
        val token = TokenManager.getToken(this)
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token not found, please login again", Toast.LENGTH_SHORT).show()
            return
        }

        val apiService = ApiClient.getRetrofitWithToken(token).create(ApiService::class.java)
        apiService.getPaymentDetails(appointmentId).enqueue(object : Callback<PaymentResponse> {
            override fun onResponse(call: Call<PaymentResponse>, response: Response<PaymentResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val paymentResponse = response.body()
                    if (paymentResponse != null) {
                        val appointment = paymentResponse.appointment
                        val paymentInfo = paymentResponse.payment_info

                        // Info Dokter
                        tvDoctorNameCard.text = appointment.doctor.name
                        tvDoctorSpecialistCard.text = appointment.doctor.specialist.name
                        Glide.with(this@PaymentActivity)
                            .load(appointment.doctor.photo)
                            .placeholder(R.drawable.profile_placeholder)
                            .into(ivDoctorImageCard)

                        // Set data ke UI
                        tvConsultation.text = appointment.consultation.name
                        tvDate.text = appointment.date
                        tvTime.text = appointment.time
                        tvStatus.text = if (appointment.status == "1") "Payment Completed" else "Waiting Payment"

                        tvBiayaKonsultasi.text = "IDR ${paymentInfo.biaya_konsultasi}"
                        tvDoctorFee.text = "IDR ${paymentInfo.doctor_fee}"
                        tvHospitalFee.text = "IDR ${paymentInfo.hospital_fee}"
                        tvVat.text = "IDR ${paymentInfo.vat}"
                        tvGrandTotal.text = "IDR ${paymentInfo.grand_total}"
                    } else {
                        Toast.makeText(this@PaymentActivity, "No payment details found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@PaymentActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                Toast.makeText(this@PaymentActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createPayment(appointmentId: Int) {
        val token = TokenManager.getToken(this)
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token not found, please login again", Toast.LENGTH_SHORT).show()
            return
        }

        val apiService = ApiClient.getRetrofitWithToken(token).create(ApiService::class.java)
        val params = mapOf("appointment_id" to appointmentId)

        apiService.createPayment(params).enqueue(object : Callback<TransactionResponse> {
            override fun onResponse(call: Call<TransactionResponse>, response: Response<TransactionResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val transactionResponse = response.body()
                    if (transactionResponse?.success == true) {
                        // Buka URL Midtrans di browser
                        val paymentUrl = transactionResponse.payment_url
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl))
                        startActivity(intent)

                        // Tunggu pengguna kembali dari Snap Midtrans
                        waitForPaymentValidation()
                    } else {
                        Toast.makeText(this@PaymentActivity, "Failed to create payment", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@PaymentActivity, "Failed to process payment", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                Toast.makeText(this@PaymentActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun waitForPaymentValidation() {
        // Simulasikan validasi hasil pembayaran setelah pengguna kembali
        // Timer sederhana atau polling untuk mengecek status pembayaran
        val token = TokenManager.getToken(this)
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token not found, please login again", Toast.LENGTH_SHORT).show()
            return
        }

        val apiService = ApiClient.getRetrofitWithToken(token).create(ApiService::class.java)

        // Cek status pembayaran setelah Snap selesai
        apiService.getPaymentDetails(appointmentId).enqueue(object : Callback<PaymentResponse> {
            override fun onResponse(call: Call<PaymentResponse>, response: Response<PaymentResponse>) {
                if (response.isSuccessful && response.body()?.appointment?.status == "1") {
                    // Pembayaran berhasil
                    redirectToSuccessActivity()
                } else {
                    // Pembayaran masih pending atau gagal
                    Toast.makeText(this@PaymentActivity, "Payment not yet completed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                Toast.makeText(this@PaymentActivity, "Error checking payment: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun redirectToSuccessActivity() {
        val intent = Intent(this, SuksesBookingActivity::class.java)
        startActivity(intent)
        finish()
    }
}
