package com.example.mytrainer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mytrainer.auth.Auth
import com.example.mytrainer.auth.Codes
import com.example.mytrainer.auth.Facebook
import com.example.mytrainer.auth.Google
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var googleAuth: Google
    private lateinit var facebookAuth: Facebook
    private val TAG: String = "Login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

   fun signUpButton(view : View){
       val email = emailInputText.text.toString()
       val password = passwordInputText.text.toString()

       if(email.equals(""))
           startActivity(Intent(this, HomeActivity::class.java))
   }
}
