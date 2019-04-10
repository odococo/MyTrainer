package com.example.mytrainer

import android.os.Bundle
import com.example.mytrainer.database.locale.Query

class StartActivity : GeneralActivity("StartActivity") {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        if (auth.isLogged()) {
            auth.toHome(Query.getInstance(this).getUser())
        } else {
            auth.toLogin()
        }
    }
}
