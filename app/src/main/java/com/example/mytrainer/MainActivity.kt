package com.example.mytrainer

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mytrainer.auth.Auth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(Auth.isLogged())
        if (!Auth.isLogged()) {
            val it = Intent(this, SocialLogin::class.java)
            startActivity(it)
        }


       /* logout.setOnClickListener {
            view ->
            Auth.logout()
            startActivity(Intent(this, SocialLogin::class.java))
        }*/

        loginButton.setOnClickListener{
            view -> Auth.isLogged()
            startActivity(Intent(this, SocialLogin::class.java))
        }
    }
}
