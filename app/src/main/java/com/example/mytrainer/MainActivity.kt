package com.example.mytrainer

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : GeneralActivity("Main") {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


       /* logout.setOnClickListener {
            view ->
            Auth.logout()
            startActivity(Intent(this, SocialLogin::class.java))
        }*/

        loginButtonMainActivity.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
