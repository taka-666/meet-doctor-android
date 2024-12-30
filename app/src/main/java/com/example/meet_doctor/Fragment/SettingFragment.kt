package com.example.meet_doctor.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.meet_doctor.LoginActivity
import com.example.meet_doctor.R
import com.example.meet_doctor.Utilitas.TokenManager

class SettingFragment : Fragment() {
    private lateinit var btnLogout: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        btnLogout = view.findViewById(R.id.btnLogout)

        btnLogout.setOnClickListener {
            // Hapus token
            TokenManager.clearToken(requireContext())

            // Periksa apakah token sudah dihapus
            val token = TokenManager.getToken(requireContext())
            if (token.isNullOrEmpty()) {
                // Token sudah dihapus, tampilkan pesan
                Log.e("Logout", "Token not found, please login again")


                // Navigasi ke halaman login
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK // Menghapus semua aktivitas sebelumnya
                startActivity(intent)
            } else {
                // Token gagal dihapus, tampilkan pesan error (opsional)
                Log.e("Logout", "Failed to Logout")
            }
        }

        return view
    }
}