package com.example.sciflaretask.util

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

const val PREFS_FILENAME = "sciflarestatefile"



const val SECRET_KEY= "secretkey"



class SFPrefs private constructor(){

    companion object {
        private var INSTANCE: SFPrefs? = null
        lateinit var prefs: SharedPreferences
        @RequiresApi(Build.VERSION_CODES.M)
        fun getInstance(context: Context): SFPrefs {

           val encriptar = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            return INSTANCE ?: SFPrefs().also {
                INSTANCE = it

                prefs = EncryptedSharedPreferences.create(context,PREFS_FILENAME, encriptar,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
            }
        }
    }


    var secretKey: String?
        get() = prefs.getString(SECRET_KEY, "")
        set(value: String?) = prefs.edit().putString(SECRET_KEY, value!!).apply()




}