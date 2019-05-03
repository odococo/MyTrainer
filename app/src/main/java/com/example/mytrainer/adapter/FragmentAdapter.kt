package com.example.mytrainer.adapter

import android.content.Context
import android.support.v4.app.*
import com.example.mytrainer.fragment.GeneralFragment

class FragmentAdapter(appContext: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments: MutableMap<Int, Fragment> = HashMap()

    init {
        val exerciseList = ExerciseListAdapter()
        fragments[0] = GeneralFragment.getInstance(appContext, exerciseList,"Giorno 1")
        fragments[1] = GeneralFragment.getInstance(appContext, exerciseList,"Giorno 2")
        fragments[2] = GeneralFragment.getInstance(appContext, exerciseList,"Giorno 3")
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val fragment = fragments[position] as GeneralFragment
        return fragment.getTitle()
    }

    override fun getItem(position: Int): Fragment? {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

}