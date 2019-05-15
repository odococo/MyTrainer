package com.example.mytrainer.utils

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.mytrainer.fragment.BlankFragment

class FragmentManager(
    val context: Context,
    private val container: Int,
    firstFragment: Fragment = BlankFragment(),
    args: Map<String, Any> = emptyMap()
) {
    private val TAG = "FragmentManager"
    private val manager = (context as AppCompatActivity).supportFragmentManager
    lateinit var currentFragment: Fragment

    init {
        switch(firstFragment, args)
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

        fun setArgs(fragment: Fragment, args: Map<String, Any>): Fragment {
            fragment.arguments = Bundle().apply {
                args.forEach { (key, value) ->
                    when (value) {
                        is String -> putString(key, value)
                        is Int -> putInt(key, value)
                        is Float -> putFloat(key, value)
                        is Boolean -> putBoolean(key, value)
                        else -> Log.e(TAG, "Tipo di $value non valido! --> ${value.javaClass}")
                    }
                }
            }

            return fragment
        }
    }
}