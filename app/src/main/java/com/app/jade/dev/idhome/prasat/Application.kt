package com.app.jade.dev.idhome.prasat

import android.app.Application
import android.content.Context

class Application: Application() {

    companion object {
        lateinit var AppContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        AppContext = applicationContext
    }

}