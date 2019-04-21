package com.example.mytrainer.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.mytrainer.fragment.GeneralFragment

class ItemsFragmentAdapter(private val fragment: GeneralFragment, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return fragment
    }

    //Numero di tab(pager) da creare. Per ogni item è uno solo.
    override fun getCount(): Int {
        return 1
    }


}