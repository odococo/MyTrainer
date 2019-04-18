package com.example.mytrainer.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.mytrainer.component.TrainingExercise
import com.example.mytrainer.component.TrainingSchedule
import com.example.mytrainer.component.User
import com.example.mytrainer.fragment.TrainingDayFragment

open class TabsDayAdapter: FragmentPagerAdapter {

    private var tabs: MutableMap<Int, Fragment>?  = null
    private var context: Context? = null

    constructor(context: Context, fm: FragmentManager) : super(fm){
        this.context = context

        initTabMap(context)
    }
    //Il seguente metodo prende in ingresso id del tab e li setta il proprio titolo.
    override fun getPageTitle(position: Int): CharSequence? {
        val fragment: TrainingDayFragment = tabs!![position] as TrainingDayFragment
        return fragment.title
    }

    override fun getItem(position: Int): Fragment? {
        return tabs?.get(position)
    }

    override fun getCount(): Int {
        return tabs!!.size
    }

    //Qui si specifica la quantità di tabs da creare, che nel nostro caso, varia in base al numero di giorni avente la scheda.
    private fun initTabMap(context: Context){
        tabs = HashMap<Int, Fragment>()

       //TODO richiamare la query che restituisce l'intera scheda di allenamento.
        //Scomporre la scheda in giorni separati(che contengono gli esercizi da fare) e passarli a TrainingDayFragment
        tabs?.put(0, TrainingDayFragment.getInstance(context,"Giorno 1"))
        tabs?.put(1, TrainingDayFragment.getInstance(context,"Giorno 2"))
        tabs?.put(2, TrainingDayFragment.getInstance(context,"Giorno 3"))

    }
}