package com.example.mytrainer.utils

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log

class FragmentManager(
    val context: Context,
    private val container: Int,
    firstFragment: Fragment
) {
    private val TAG = "FragmentManager"
    private val manager = (context as AppCompatActivity).supportFragmentManager
    var currentFragment: Fragment = firstFragment

    init {
        switch(firstFragment)
    }

    fun switch(
        fragment: Fragment,
        args: Map<String, Any> = emptyMap()
    ) {
        setArgs(fragment, args)
        manager
            .beginTransaction()
            .replace(container, fragment)
            .commit()
        currentFragment = fragment
    }

    companion object {
        private const val TAG = "FragmentManager"

        fun setArgs(fragment: Fragment, args: Map<String, Any>) {
            fragment.arguments = Bundle().apply {
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
}