package com.example.mytrainer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.mytrainer.auth.Codes
import com.example.mytrainer.auth.Facebook
import com.example.mytrainer.auth.Google
import com.example.mytrainer.auth.Telegram
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : GeneralActivity("Login") {

    private lateinit var googleAuth: Google
    private lateinit var facebookAuth: Facebook
    private lateinit var telegramAuth: Telegram

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        googleAuth = Google(this, google_sign_in_button)
        facebookAuth = Facebook(this, facebook_sign_in_button)
        //telegramAuth = Telegram(this, telegram_sign_in_button)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "sign in with $requestCode and $resultCode and ${data?.data ?: "no data"}")
        super.onActivityResult(requestCode, resultCode, data)

        val code = Codes.fromInt(requestCode)

        when (code) {
            Codes.GOOGLE_SIGN_IN -> googleAuth.handleResult(requestCode, resultCode, data)
            Codes.FACEBOOK_SIGN_IN -> facebookAuth.handleResult(requestCode, resultCode, data)
            Codes.TELEGRAM_SIGN_IN -> telegramAuth.handleResult(requestCode, resultCode, data)
            else -> Log.w(TAG, "Code $requestCode is not valid")
        }
    }

   fun signUpButton(){
       val email = emailInputText.text.toString()
       val password = passwordInputText.text.toString()

       if(email.isEmpty() && password.isEmpty())
           startActivity(Intent(this, HomeActivity::class.java))
   }
}
