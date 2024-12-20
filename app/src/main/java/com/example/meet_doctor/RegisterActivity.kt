package com.example.meet_doctor

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

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
        btnSignup = findViewById(R.id.btnSignup)  // Inisialisasi btnSignup
        btnSignin = findViewById(R.id.btnSignin)  // Inisialisasi btnSignin

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
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Aksi untuk tombol Sign In (kembali ke LoginActivity)
        btnSignin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
