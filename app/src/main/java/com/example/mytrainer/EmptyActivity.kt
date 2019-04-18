package com.example.mytrainer

import android.os.Bundle
import com.example.mytrainer.database.locale.Query

class EmptyActivity : GeneralActivity("EmptyActivity") {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)

        if (auth.isLogged()) {
            auth.toHome(Query.getInstance(this).getUser())
        } else {
            auth.toLogin()
        }
    }
}
