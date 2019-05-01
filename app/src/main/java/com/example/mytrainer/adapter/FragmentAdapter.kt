package com.example.mytrainer.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.mytrainer.fragment.ExerciseListFragment

class FragmentAdapter(private val fragments: MutableMap<Int, Fragment>, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        val fragment = fragments[position] as ExerciseListFragment
        return fragment.getTitle()
    }

    override fun getItem(position: Int): Fragment? {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }


}