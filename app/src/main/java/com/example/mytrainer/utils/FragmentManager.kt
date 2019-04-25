package com.example.mytrainer.utils

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

class FragmentManager(
    val context: Context,
    val container: Int
) {
    private val manager = (context as AppCompatActivity).supportFragmentManager
    private lateinit var currentFragment: Fragment

    fun switch(fragment: Fragment) {
        manager
            .beginTransaction()
            .replace(container, fragment)
            .commit()
        currentFragment = fragment
    }
}