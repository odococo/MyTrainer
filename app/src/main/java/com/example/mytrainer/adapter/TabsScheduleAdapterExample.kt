package com.example.mytrainer.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.mytrainer.fragment.ExercisFramgentExample

open class TabsScheduleAdapterExample: FragmentPagerAdapter {

    private var tabs: MutableMap<Int, Fragment>?  = null
    private var context: Context? = null

    constructor(context: Context, fm: FragmentManager) : super(fm){
        this.context = context

        initTabMap(context)
    }

    override fun getItem(position: Int): Fragment? {
        return tabs?.get(position)
    }

    override fun getCount(): Int {
        return tabs!!.size
    }

    private fun initTabMap(context: Context){
        tabs = HashMap<Int, Fragment>()
        tabs?.put(0, ExercisFramgentExample.getInstance(context))

    }

}