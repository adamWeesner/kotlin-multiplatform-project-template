package com.weesnerDevelopment.lavalamp.android

import android.app.Application
import kimchi.Kimchi
import kimchi.logger.android.AdbWriter

class LavalampApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Kimchi.addLog(AdbWriter())
    }
}