package com.example.mytrainer.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.mytrainer.fragment.GeneralFragment

class FragmentAdapter(private val fragments: MutableMap<Int, GeneralFragment>, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        val fragment: GeneralFragment = fragments[position] as GeneralFragment
        return "Titolo del frammento"
    }

    override fun getItem(position: Int): Fragment? {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }


}