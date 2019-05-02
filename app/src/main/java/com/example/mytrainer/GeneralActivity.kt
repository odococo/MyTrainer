package com.example.mytrainer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.mytrainer.auth.Auth

open class GeneralActivity(
    val TAG: String = "General"
): AppCompatActivity() {
    lateinit var auth: Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "Create activity")
        // inizializzazione singleton
        auth = Auth.getInstance(this)

        if (auth.isLogged() && this !is MainActivity) auth.toHome()
        else if (!auth.isLogged() && this !is LoginActivity) auth.toLogin()
    }
}
