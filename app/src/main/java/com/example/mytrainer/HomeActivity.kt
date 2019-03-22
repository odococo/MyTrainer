package com.example.mytrainer

import android.os.Bundle
import com.example.mytrainer.auth.Auth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : GeneralActivity("Home") {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        logout.setOnClickListener {
            Auth(this).logout()
        }
    }
}
