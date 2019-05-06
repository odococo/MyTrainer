package com.example.mytrainer.adapter

import android.content.Context
import android.support.v4.app.*
import com.example.mytrainer.component.TrainingExercise
import com.example.mytrainer.fragment.GeneralFragment

class FragmentAdapter(appContext: Context, schedule: List<TrainingExercise>, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments: MutableMap<Int, Fragment> = HashMap()
    private val pageTitles: MutableMap<Int, String> = HashMap()

    private var schedule: List<TrainingExercise> = schedule

    init {
        //  println(schedule)
        val exerciseList = ExerciseListAdapter()
        fragments[0] = GeneralFragment.getInstance(appContext, exerciseList)
        pageTitles[0] = "Giorno 1"
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