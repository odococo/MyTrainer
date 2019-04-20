package com.example.mytrainer.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.mytrainer.fragment.ProfileFragment

class ProfileTabAdapter(private var context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private lateinit var tabs: MutableMap<Int, Fragment>

    init {
        initTabMap(context)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val fragment: ProfileFragment = tabs[position] as ProfileFragment
        return fragment.title
    }

    override fun getItem(position: Int): Fragment? {
        return tabs[position]
    }

    override fun getCount(): Int {
        return tabs.size
    }

    private fun initTabMap(context: Context){
        tabs = HashMap<Int, Fragment>()
        tabs[0] = ProfileFragment.getInstance(context, "Profile")
    }

}