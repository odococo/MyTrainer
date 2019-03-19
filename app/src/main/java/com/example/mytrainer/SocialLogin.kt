package com.example.mytrainer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import com.example.mytrainer.auth.*

import kotlinx.android.synthetic.main.activity_social_login.*

const val TAG = "SocialLogin"

class SocialLogin : AppCompatActivity() {
    lateinit var googleAuth: Google
    lateinit var facebookAuth: Facebook

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_login)

        googleAuth = Google(this, google_sign_in_button)
        facebookAuth = Facebook(this, facebook_sign_in_button)

        if (Auth.isLogged()) Log.i(TAG, "User already logged")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "sign in with $requestCode and $resultCode and ${data?.data ?: "no data"}")
        super.onActivityResult(requestCode, resultCode, data)

        val code = Codes.fromInt(requestCode)

        when (code) {
            Codes.GOOGLE_SIGN_IN -> googleAuth.handleResult(requestCode, resultCode, data)
            Codes.FACEBOOK_SIGN_IN -> facebookAuth.handleResult(requestCode, resultCode, data)
            else -> Log.w(TAG, "Code $requestCode is not valid")
        }
    }

}
