package com.example.mytrainer.adapter.trainer

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.example.mytrainer.GeneralActivity
import com.example.mytrainer.component.ScheduleRequest
import com.example.mytrainer.component.TrainingExercise
import com.example.mytrainer.fragment.trainer.CreateGeneralFragment
import com.example.mytrainer.fragment.trainer.CreateTrainingDayFragment
import com.example.mytrainer.utils.FragmentManager

class ScheduleAdapter(
    context: Context,
    private val days: Int,
    private val request: ScheduleRequest
) : FragmentPagerAdapter((context as GeneralActivity).supportFragmentManager) {
    private val trainingDays = mutableListOf<CreateTrainingDayFragment>()

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = FragmentManager.setArgs(
                    CreateGeneralFragment(),
                    mapOf(
                        CreateGeneralFragment.ATHLETE to request.athlete.id,
                        CreateGeneralFragment.INFO to request.info
                    )
                )
                fragment
            }
            else -> {
                val fragment = FragmentManager.setArgs(
                    CreateTrainingDayFragment(),
                    mapOf(CreateTrainingDayFragment.DAY to position)
                )
                trainingDays.add(fragment as CreateTrainingDayFragment)
                fragment
            }
        }
    }

    override fun getCount(): Int {
        return days + 1
    }

    fun getExercises(): List<TrainingExercise> {
        val list = mutableListOf<TrainingExercise>()
        for (trainingDay in trainingDays) {
            val exercises = trainingDay.exercises
            if (exercises.isEmpty()) {
                return emptyList()
            }
            list.addAll(exercises)
        }

        return list
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Generale"
            else -> "Giorno $position"
        }
    }
}