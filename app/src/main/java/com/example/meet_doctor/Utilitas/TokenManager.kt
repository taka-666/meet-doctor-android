package com.example.meet_doctor.Utilitas
import android.content.Context

object TokenManager {
        private const val PREF_NAME = "UserTokenPreferences"
        private const val KEY_TOKEN = "auth_token"

        /**
         * Save the token in SharedPreferences.
         */
        fun saveToken(context: Context, token: String) {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(KEY_TOKEN, token)
            editor.apply()
        }

        /**
         * Retrieve the token from SharedPreferences.
         */
        fun getToken(context: Context): String? {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(KEY_TOKEN, null)
        }

        /**
         * Clear the token (if needed for logout).
         */
        fun clearToken(context: Context) {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove(KEY_TOKEN)
            editor.apply()
        }
    }