package com.example.mytrainer.adapter.trainer

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.mytrainer.GeneralActivity
import com.example.mytrainer.fragment.trainer.CreateGeneralFragment
import com.example.mytrainer.fragment.trainer.CreateTrainingDayFragment
import com.example.mytrainer.utils.FragmentManager

class ScheduleAdapter(
    context: Context,
    var days: Int
) : FragmentStatePagerAdapter((context as GeneralActivity).supportFragmentManager) {

    override fun getItem(position: Int): Fragment {
        val fragment = when (position) {
            0 -> CreateGeneralFragment()
            else -> FragmentManager.setArgs(
                CreateTrainingDayFragment(),
                mapOf(CreateTrainingDayFragment.DAY to position)
            )
        }

        return fragment
    }

    override fun getCount(): Int {
        return days + 1
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Generale"
            else -> "Giorno $position"
        }
    }
}