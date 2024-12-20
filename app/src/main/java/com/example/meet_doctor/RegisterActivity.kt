package com.example.meet_doctor

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.meet_doctor.Interface.ApiService
import com.example.meet_doctor.Model.LoginRequest
import com.example.meet_doctor.Model.LoginResponse
import com.example.meet_doctor.Model.RegisterRequest
import com.example.meet_doctor.Model.RegisterResponse
import com.example.meet_doctor.Utilitas.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var edNameInput: EditText
    private lateinit var edEmailInput: EditText
    private lateinit var edPasswordInput: EditText
    private lateinit var edConfirmPasswordInput: EditText
    private lateinit var btnSignin: Button
    private lateinit var btnSignup: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inisialisasi View
        edNameInput = findViewById(R.id.edNameInput)
        edEmailInput = findViewById(R.id.edEmailInput)
        edPasswordInput = findViewById(R.id.edPasswordInput)
        edConfirmPasswordInput = findViewById(R.id.edConfirmPassword)
        btnSignup = findViewById(R.id.btnSignup)
        btnSignin = findViewById(R.id.btnSignin)

        // Aksi untuk tombol Sign Up
        btnSignup.setOnClickListener {
            val name = edNameInput.text.toString().trim()
            val email = edEmailInput.text.toString().trim()
            val password = edPasswordInput.text.toString().trim()
            val confirmPassword = edConfirmPasswordInput.text.toString().trim()

            // Validasi input
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                // Validasi jika password dan konfirmasi password tidak cocok
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Intent ke LoginActivity setelah proses registrasi
                registerUser(name, email, password, confirmPassword)
            }
        }

        // Aksi untuk tombol Sign In (kembali ke LoginActivity)
        btnSignin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun registerUser(name: String, email: String, password: String, confirmPassword: String) {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val registerRequest = RegisterRequest(name, email, password, confirmPassword)

        apiService.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse?.success == true) {
                        Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "API Response: ${registerResponse?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "HTTP Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
