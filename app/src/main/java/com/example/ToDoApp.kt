package com.example

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp



@HiltAndroidApp
class ToDoApp:Application() {

    override fun onCreate() {
        super.onCreate()

//        if (BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(
//                StrictMode.ThreadPolicy.Builder()
//                    .detectAll()              // Detect everything (disk, network, etc.)
//                    .penaltyLog()             // Log to Logcat
//                    .penaltyFlashScreen()     // Flash screen if violated (optional)
//                    .build()
//            )
//
//            StrictMode.setVmPolicy(
//                StrictMode.VmPolicy.Builder()
//                    .detectAll()
//                    .penaltyLog()
//                    .build()
//            )
//        }
    }
}