package com.example.mytrainer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.mytrainer.fragment.TrainingDayFragment

open class TabsAdapter(private var context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private lateinit var tabs: MutableMap<Int, Fragment>

    init {
        initTabMap(context)
    }

    //Il seguente metodo prende in ingresso id del tab e li setta il proprio titolo.
    override fun getPageTitle(position: Int): CharSequence? {
       val fragment: TrainingDayFragment = tabs[position] as TrainingDayFragment
        return fragment.title
    }

    override fun getItem(position: Int): Fragment? {
        return tabs[position]
    }

    override fun getCount(): Int {
        return tabs.size
    }

    //Qui si specifica la quantità di tabs da creare, che nel nostro caso, varia in base al numero di giorni avente la scheda.
    @SuppressLint("UseSparseArrays")
    private fun initTabMap(context: Context){
        tabs = HashMap<Int, Fragment>()

       //TODO richiamare la query che restituisce l'intera scheda di allenamento.
        //Scomporre la scheda in giorni separati(che contengono gli esercizi da fare) e passarli a TrainingDayFragment
        tabs[0] = TrainingDayFragment.getInstance(context,"Giorno 1")
        tabs[1] = TrainingDayFragment.getInstance(context,"Giorno 2")
        tabs[2] = TrainingDayFragment.getInstance(context,"Giorno 3")

    }

}