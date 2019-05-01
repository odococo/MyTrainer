package com.example.mytrainer.utils

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log

class FragmentManager(
    val context: Context,
    val container: Int
) {
    private val TAG = "FragmentManager"
    private val manager = (context as AppCompatActivity).supportFragmentManager
    private lateinit var currentFragment: Fragment

    fun switch(
        fragment: Fragment,
        args: Map<String, Any> = emptyMap()
    ) {
        fragment.arguments = setArgs(args)
        manager
            .beginTransaction()
            .replace(container, fragment)
            .commit()
        currentFragment = fragment
    }

    private fun setArgs(args: Map<String, Any>): Bundle {
        return Bundle().apply {
            args.forEach { (key, value) ->
                when (value) {
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    is Float -> putFloat(key, value)
                    else -> Log.e(TAG, "Tipo di $value non valido! --> ${value.javaClass}")
                }
            }
        }
    }
}