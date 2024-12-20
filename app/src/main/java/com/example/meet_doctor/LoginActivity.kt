package com.example.meet_doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import com.example.meet_doctor.Interface.ApiService
import com.example.meet_doctor.Model.LoginRequest
import com.example.meet_doctor.Model.LoginResponse
import com.example.meet_doctor.Utilitas.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Button
import android.widget.EditText
import com.example.meet_doctor.Fragment.HomeFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var edEmailInput: EditText
    private lateinit var edPasswordInput: EditText
    private lateinit var btnSignin: Button
    private lateinit var btnSignup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inisialisasi View
        edEmailInput = findViewById(R.id.edEmailInput)
        edPasswordInput = findViewById(R.id.edPasswordInput)
        btnSignin = findViewById(R.id.btnSignin)
        btnSignup = findViewById(R.id.btnSignup)

        // Set aksi untuk tombol Sign In
        btnSignin.setOnClickListener {
            val email = edEmailInput.text.toString().trim()
            val password = edPasswordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }

        btnSignup.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)

            // Memulai activity tujuan
            startActivity(intent)

            // Menutup LoginActivity setelah berpindah halaman
        }
    }

    private fun loginUser(email: String, password: String) {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val loginRequest = LoginRequest(email = email, password = password)

        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()
                    if (loginResponse?.success == true) {
                        Toast.makeText(this@LoginActivity, "Welcome, ${loginResponse.user?.name}", Toast.LENGTH_SHORT).show()
                        // Simpan token untuk kebutuhan selanjutnya
                        val token = loginResponse.token
                        // Navigasi ke halaman lain

                        // Intent untuk berpindah ke HomeActivity
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)

                        // Jika ingin mengirimkan token atau data lain, bisa ditambahkan
                        intent.putExtra("TOKEN", token)

                        // Memulai activity tujuan
                        startActivity(intent)

                        // Menutup LoginActivity setelah berpindah halaman
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, loginResponse?.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

