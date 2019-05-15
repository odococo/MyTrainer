package com.example.mytrainer.adapter

import android.content.Context
import android.support.v4.app.*
import android.view.ViewGroup
import com.example.mytrainer.component.TrainingSchedule
import com.example.mytrainer.fragment.GeneralFragment
import com.example.mytrainer.fragment.login.LoadingFragment

class FragmentAdapter(
    appContext: Context,
    val schedule: TrainingSchedule,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    private val fragments: ArrayList<Fragment> = ArrayList()
    private val pageTitles: ArrayList<String> = ArrayList()

    init {
        // fragments[0] = LoadingFragment() // se assente uno schedule potrebbe essere la pagina che lo invita a chiedere una scheda oppure il riepilogo della stessa
       // pageTitles[0] = "Generale"

        for ((day, exercises) in schedule.perDay()) {
            fragments.add(GeneralFragment.getInstance(appContext,ExerciseListAdapter(exercises)))
            pageTitles.add("Giorno $day")
        }
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