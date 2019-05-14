package com.example.mytrainer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.mytrainer.auth.Auth
import com.example.mytrainer.component.User
import com.example.mytrainer.utils.FragmentManager

open class GeneralActivity(
    val TAG: String = "General"
): AppCompatActivity() {
    lateinit var auth: Auth
    lateinit var user: User
    protected lateinit var manager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "Create activity")
        // inizializzazione singleton
        auth = Auth.getInstance(this)
        user = auth.getUser()
    }
}
