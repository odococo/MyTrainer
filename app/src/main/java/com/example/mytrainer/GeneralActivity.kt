package com.example.mytrainer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mytrainer.auth.Auth
import com.example.mytrainer.database.locale.Query

open class GeneralActivity(
    val TAG: String = "General"
): AppCompatActivity() {
    protected lateinit var auth: Auth
    protected lateinit var db: Query

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Auth(this)
        auth.checkLogin()
    }
}
