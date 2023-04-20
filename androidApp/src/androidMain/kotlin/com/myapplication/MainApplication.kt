package com.myapplication

import android.app.Application
import com.example.data.local.Manager

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Manager.context = this
    }

}