package com.example.mytrainer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.mytrainer.auth.Auth
import com.example.mytrainer.auth.Codes
import com.example.mytrainer.auth.Facebook
import com.example.mytrainer.auth.Google
import com.example.mytrainer.fragment.login.LoadingFragment
import com.example.mytrainer.fragment.login.LoginFragment
import com.example.mytrainer.utils.FragmentManager

class LoginActivity : GeneralActivity("LoginActivity") {
    private lateinit var manager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (auth.isLogged()) {
            manager = FragmentManager(this, R.id.root_layout, LoadingFragment(), mapOf("force" to true))
        } else {
            manager = FragmentManager(this, R.id.root_layout, LoginFragment())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "sign in with $requestCode and $resultCode and ${data?.data ?: "no data"}")
        val loading = LoadingFragment()
        manager.switch(loading)

        val auth: Auth? = when (Codes.fromInt(requestCode)) {
            Codes.GOOGLE_SIGN_IN -> {
                Google.getInstance()
            }
            Codes.FACEBOOK_SIGN_IN -> {
                Facebook.getInstance()
            }
            else -> {
                Log.w(TAG, "Code $requestCode is not valid")
                null
            }
        }
        if (auth != null) {
            auth.setSuccessfulLogin { loading.init() }
            auth.setFailedLogin { manager.switch(LoginFragment()) }
            auth.handleResult(requestCode, resultCode, data)
        }
    }

}
