package com.example.mytrainer

import android.app.Activity
import android.os.Bundle
import com.example.mytrainer.auth.Auth

open class GeneralActivity(
    val TAG: String = "General"
): Activity() {
    protected lateinit var auth: Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Auth(this)
        auth.checkLogin()
    }
}
