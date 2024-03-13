package com.example.sciflaretask

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.sciflaretask.util.SFPrefs
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class SciFlareApplication:Application() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        super.onCreate()
        application = this
        publicPrefs = SFPrefs.getInstance(applicationContext!!)
    }

    companion object{
        lateinit var publicPrefs:SFPrefs
        lateinit var application:SciFlareApplication
            private set

    }
}