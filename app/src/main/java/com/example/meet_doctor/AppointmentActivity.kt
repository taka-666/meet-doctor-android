package com.example.meet_doctor

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.meet_doctor.Interface.ApiService
import com.example.meet_doctor.Model.AppointmentRequest
import com.example.meet_doctor.Model.AppointmentResponse
import com.example.meet_doctor.Utilitas.ApiClient
import com.example.meet_doctor.Utilitas.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class AppointmentActivity : AppCompatActivity() {

    private lateinit var tvDoctorName: TextView
    private lateinit var tvDoctorSpecialist: TextView
    private lateinit var ivDoctorImage: ImageView
    private lateinit var btnBookAppointment: Button
    private lateinit var edConsultation: EditText
    private lateinit var edTimeInput: EditText
    private lateinit var edDateInput: EditText

    private var doctorId: Int = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        // Inisialisasi View
        tvDoctorName = findViewById(R.id.tvDoctorName)
        ivDoctorImage = findViewById(R.id.ivDoctorImage)
        tvDoctorSpecialist = findViewById(R.id.tvDoctorSpecialist)
        btnBookAppointment = findViewById(R.id.btnBook)
        edConsultation = findViewById(R.id.edConsultation)
        edDateInput = findViewById(R.id.edDateInput)
        edTimeInput = findViewById(R.id.edTimeInput)

        // Ambil data dokter dari Intent
        doctorId = intent.getIntExtra("DOCTOR_ID", -1)
        val doctorName = intent.getStringExtra("DOCTOR_NAME")
        val doctorImage = intent.getStringExtra("DOCTOR_IMAGE")
        val doctorSpecialist = intent.getStringExtra("DOCTOR_SPECIALIST")

        // Validasi doctorId
        if (doctorId == -1) {
            Toast.makeText(this, "Invalid doctor data", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Tampilkan data dokter
        tvDoctorName.text = doctorName
        tvDoctorSpecialist.text = doctorSpecialist
        Glide.with(this).load(doctorImage).placeholder(R.drawable.placeholder_image).into(ivDoctorImage)

        // Date Picker
        edDateInput.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay" // Format API (YYYY-MM-DD)
                    edDateInput.setText(formattedDate)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        // Tombol untuk booking appointment
        btnBookAppointment.setOnClickListener {
            val consultationTopic = edConsultation.text.toString().trim()
            val date = edDateInput.text.toString().trim()
            val time = edTimeInput.text.toString().trim()

            if (consultationTopic.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                createAppointment(consultationTopic, date, time)
            }
        }
    }

    private fun createAppointment(consultationTopic: String, date: String, time: String) {
        // Ambil token dari TokenManager
        val token = TokenManager.getToken(this)
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token not found, please login again", Toast.LENGTH_SHORT).show()
            return
        }

        // Konfigurasikan API Service dengan token
        val apiService = ApiClient.getRetrofitWithToken(token).create(ApiService::class.java)
        val appointmentRequest = AppointmentRequest(
            doctor_id = doctorId,
            consultation_topic = consultationTopic,
            date = date,
            time = time
        )

        apiService.createAppointment(appointmentRequest).enqueue(object : Callback<AppointmentResponse> {
            override fun onResponse(call: Call<AppointmentResponse>, response: Response<AppointmentResponse>) {
                if (response.isSuccessful) {
                    val appointmentResponse = response.body()
                    if (appointmentResponse?.success == true) {
                        Toast.makeText(this@AppointmentActivity, "Appointment booked successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(this@AppointmentActivity, "Server Error: $errorBody", Toast.LENGTH_LONG).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(this@AppointmentActivity, "Server Error: $errorBody", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<AppointmentResponse>, t: Throwable) {
                Toast.makeText(this@AppointmentActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
