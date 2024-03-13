package com.example.sciflaretask.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.sciflaretask.R
import com.example.sciflaretask.SciFlareApplication
import com.example.sciflaretask.util.Utils
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private var imgSplash: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()

    }

    private fun initView() {
        imgSplash=findViewById(R.id.imgSplash)

        loadDashboard()



    }

    fun loadDashboard() {

        if (SciFlareApplication.publicPrefs.secretKey.isNullOrEmpty()) {
            generateSecretKey()
        } else {
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({


                val intent = Intent(this, SenderActivity::class.java)
                startActivity(intent)
                finish()

            }, 3000)


        }

    }

    private fun generateSecretKey() {
        val secretKey= Utils.generateKey()
        val encodedKey: String = Base64.encodeToString(secretKey.encoded,Base64.DEFAULT)
        Log.v("SecretKey**",encodedKey)
        SciFlareApplication.publicPrefs.secretKey=encodedKey
        loadDashboard()




    }
}