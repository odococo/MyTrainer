package com.example.mytrainer

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loginButtonMainActivity.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
