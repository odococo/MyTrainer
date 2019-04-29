package com.example.mytrainer.fragment

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View

open class GeneralFragment: Fragment() {

    private lateinit var anotherView: View
    private lateinit var adapter: RecyclerView.Adapter<*>

    fun setAdapter(adapter: RecyclerView.Adapter<*>): GeneralFragment {
        this.adapter = adapter

        return this
    }

    fun setAnotherView(view: View): GeneralFragment {
        this.anotherView = view

        return this
    }

}