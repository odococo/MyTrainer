package com.example.mytrainer.adapter

import android.content.Context
import android.support.v4.app.*
import com.example.mytrainer.fragment.GeneralFragment

class FragmentAdapter(appContext: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments: MutableMap<Int, Fragment> = HashMap()
    private val pageTitles: MutableMap<Int, String> = HashMap()

    init {
        val exerciseList = ExerciseListAdapter()
        fragments[0] = GeneralFragment.getInstance(appContext, exerciseList)
        fragments[1] = GeneralFragment.getInstance(appContext, exerciseList)
        fragments[2] = GeneralFragment.getInstance(appContext, exerciseList)

        pageTitles[0] = "Giorno 1"
        pageTitles[1] = "Giorno 2"
        pageTitles[2] = "Giorno 3"
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