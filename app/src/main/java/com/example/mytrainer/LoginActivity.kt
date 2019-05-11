package com.example.mytrainer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.mytrainer.auth.Facebook
import com.example.mytrainer.auth.Google
import com.example.mytrainer.fragment.login.LoadingFragment
import com.example.mytrainer.fragment.login.LoginFragment
import com.example.mytrainer.utils.FragmentManager

class LoginActivity : GeneralActivity("LoginActivity") {
    private lateinit var manager: FragmentManager
    private lateinit var loading: LoadingFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loading = LoadingFragment()

        if (auth.isLogged()) auth.logout()

        auth.setSuccessfulLogin { loading.init() }
        auth.setFailedLogin { manager.switch(LoginFragment()) }

        manager = if (auth.isLogged()) {
            FragmentManager(this, R.id.root_layout, loading, mapOf("logged" to true))
        } else {
            FragmentManager(this, R.id.root_layout, LoginFragment())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "sign in with $requestCode and $resultCode and ${data?.data ?: "no data"}")
        val loading = LoadingFragment()
        manager.switch(loading)

        val auth = when (requestCode) {
            Codes.GOOGLE_SIGN_IN.code -> {
                Google.getInstance()
            }
            Codes.FACEBOOK_SIGN_IN.code -> {
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
