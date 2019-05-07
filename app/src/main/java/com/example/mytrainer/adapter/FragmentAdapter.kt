package com.example.mytrainer.adapter

import android.content.Context
import android.support.v4.app.*
import com.example.mytrainer.component.TrainingExercise
import com.example.mytrainer.component.TrainingSchedule
import com.example.mytrainer.fragment.GeneralFragment
import com.example.mytrainer.fragment.login.LoadingFragment

class FragmentAdapter(
    appContext: Context,
    val schedule: TrainingSchedule,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    private val fragments: MutableMap<Int, Fragment> = HashMap()
    private val pageTitles: MutableMap<Int, String> = HashMap()

    private var trainingDays: HashMap<Int, List<TrainingExercise>> = HashMap()

    init {
        fragments[0] = LoadingFragment() // se assente uno schedule potrebbe essere la pagina che lo invita a chiedere una scheda oppure il riepilogo della stessa
        pageTitles[0] = "Generale"
        for ((day, exercises) in schedule.perDay()) {
            fragments[day] = GeneralFragment.getInstance(appContext, ExerciseListAdapter(exercises))
            pageTitles[day] = "Giorno $day"
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