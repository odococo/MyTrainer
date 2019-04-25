package com.example.mytrainer

import android.os.Bundle

// serve solo per indirizzare a login oppure al main
// e' gestito da general activity e verrebbe comunque fatto dalla LoginFragment Activity
// ma prima di cio' caricherebbe il layout
class EmptyActivity : GeneralActivity("EmptyActivity") {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)
    }
}
