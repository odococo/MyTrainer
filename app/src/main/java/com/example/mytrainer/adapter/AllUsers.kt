package com.example.mytrainer.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.example.mytrainer.GeneralActivity
import com.example.mytrainer.database.locale.Query
import com.example.mytrainer.fragment.admin.UsersFragment

class AllUsers(
    context: Context
) : FragmentPagerAdapter((context as GeneralActivity).supportFragmentManager) {
    override fun getItem(position: Int): Fragment {
        val fragment = UsersFragment()
        fragment.users = when (position) {
            0 -> Query.getInstance().getAllUsers()
            1 -> Query.getInstance().getAllUsersType("athlete")
            2 -> Query.getInstance().getAllUsersType("trainer")
            3 -> Query.getInstance().getAllUsersType("admin")
            else -> emptyList()
        }
        fragment.positionInViewPager = position
        println(fragment.users)

        return fragment
    }

    override fun getItemPosition(`object`: Any?): Int {
        return super.getItemPosition(`object`)
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            1 -> "Atleti"
            2 -> "Istruttori"
            3 -> "Admin"
            else -> "Tutti"
        }
    }
}