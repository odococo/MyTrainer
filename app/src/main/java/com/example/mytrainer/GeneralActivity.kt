package com.example.mytrainer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mytrainer.auth.Auth

open class GeneralActivity(
    val TAG: String = "General"
): AppCompatActivity() {
    protected lateinit var auth: Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Auth(this)
        auth.checkLogin()
    }
}
