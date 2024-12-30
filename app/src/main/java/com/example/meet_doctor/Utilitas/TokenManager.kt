package com.example.meet_doctor.Utilitas
import android.content.Context

object TokenManager {
        private const val PREF_NAME = "UserTokenPreferences"
        private const val KEY_TOKEN = "auth_token"

        fun saveToken(context: Context, token: String) {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(KEY_TOKEN, token)
            editor.apply()
        }

        fun getToken(context: Context): String? {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(KEY_TOKEN, null)
        }

        fun clearToken(context: Context) {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove(KEY_TOKEN)
            editor.apply()
        }
    }