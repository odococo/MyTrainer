package com.example.mytrainer

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mytrainer.auth.Auth

open class GeneralActivity(
    val TAG: String = "General"
): Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Auth(this).checkLogin()
    }
}
