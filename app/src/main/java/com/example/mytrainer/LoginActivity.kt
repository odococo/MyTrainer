package com.example.mytrainer

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import com.example.mytrainer.auth.Codes
import com.example.mytrainer.auth.Facebook
import com.example.mytrainer.auth.Google
import com.example.mytrainer.auth.Telegram
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : GeneralActivity("Login"), Toolbar.OnMenuItemClickListener {

    private lateinit var googleAuth: Google
    private lateinit var facebookAuth: Facebook
    private lateinit var telegramAuth: Telegram
    private var toolbar:Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initToolbar()

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

    private fun initToolbar(){
        this.toolbar = findViewById(R.id.toolbar)
        this.toolbar?.setTitle(R.string.app_name)
        this.toolbar?.setOnMenuItemClickListener(this)
        this.toolbar?.inflateMenu(R.menu.menu_toolbar)
    }
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return false
    }

   fun signInButton(view: View){
       val email = emailInputText.text.toString()
       val password = passwordInputText.text.toString()

       if(email.isEmpty() && password.isEmpty())
           startActivity(Intent(this, HomeActivity::class.java))
   }
}
