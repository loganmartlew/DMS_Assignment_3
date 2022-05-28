package com.assign3.client

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class AuthService : Service() {
    private val binder = AuthBinder()

    inner class AuthBinder : Binder() {
        fun getService(): AuthService = this@AuthService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}