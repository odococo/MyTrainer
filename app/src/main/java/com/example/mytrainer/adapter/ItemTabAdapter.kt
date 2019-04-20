package com.example.mytrainer.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.mytrainer.fragment.GeneralFragment

class ItemTabAdapter(private val fragment: GeneralFragment, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return fragment
    }

    override fun getCount(): Int {
        return 1
    }

}