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
    //Il seguente metodo prende in ingresso id del tab e li setta il proprio titolo.
    override fun getPageTitle(position: Int): CharSequence? {
        val fragment: ExercisFramgentExample = tabs!!.get(position) as ExercisFramgentExample
        val title: String = fragment.title
        return title
    }

    override fun getItem(position: Int): Fragment? {
        return tabs?.get(position)
    }

    override fun getCount(): Int {
        return tabs!!.size
    }

    //Qui si specifica la quantità di tab da creare, che nel nostro caso, varia in base al numero di giorni avente la scheda.
    private fun initTabMap(context: Context){
        tabs = HashMap<Int, Fragment>()
        tabs?.put(0, ExercisFramgentExample.getInstance(context))
        tabs?.put(1, ExercisFramgentExample.getInstance(context))

    }
}