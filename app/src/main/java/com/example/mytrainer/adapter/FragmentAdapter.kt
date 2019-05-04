package com.example.mytrainer.adapter

import android.content.Context
import android.support.v4.app.*
import com.example.mytrainer.component.TrainingSchedule
import com.example.mytrainer.database.locale.Query
import com.example.mytrainer.fragment.GeneralFragment

class FragmentAdapter(appContext: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments: MutableMap<Int, Fragment> = HashMap()
    private val pageTitles: MutableMap<Int, String> = HashMap()

    private val schedule = Query.getInstance(appContext).getCurrentSchedule()

    init {

        println(schedule)

        val exerciseList = ExerciseListAdapter()
        fragments[0] = GeneralFragment.getInstance(appContext, exerciseList)

        pageTitles[0] = "Giorno 1"
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pageTitles[position]
    }

    override fun getItem(position: Int): Fragment? {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

}