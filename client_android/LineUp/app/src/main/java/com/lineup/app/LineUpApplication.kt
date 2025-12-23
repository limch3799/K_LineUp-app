package com.lineup.app

import android.app.Application
import com.google.firebase.FirebaseApp

class LineUpApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Firebase 초기화
        FirebaseApp.initializeApp(this)
    }
}