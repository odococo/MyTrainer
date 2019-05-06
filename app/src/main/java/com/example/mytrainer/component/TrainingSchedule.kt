package com.example.mytrainer.component

import android.util.Log
import com.example.mytrainer.database.locale.Query
import com.example.mytrainer.database.locale.SQLContract
import com.example.mytrainer.database.remote.FirebaseContract
import java.util.*

data class TrainingSchedule(
    var trainer: User,
    var athlete: User,
    var startDate: Date,
    var exercises: List<TrainingExercise>
) : Component() {

    constructor() : this(User(), User(), Date(), listOf(TrainingExercise()))

    override fun withId(id: String): TrainingSchedule {
        this.id = id

        return this
    }

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        FirebaseContract.TrainingSchedules.TRAINER to trainer,
        FirebaseContract.TrainingSchedules.ATHLETE to athlete,
        FirebaseContract.TrainingSchedules.STARTDATE to startDate,
        FirebaseContract.TrainingSchedules.EXERCISES to exercises
    )

    override fun fromMap(map: Map<String, Any?>): TrainingSchedule {
        val trainingSchedule = TrainingSchedule()
        println("La mappa e' ${map}")
        map.forEach { (key, value) ->
            when (key) {
                SQLContract.TrainingSchedules.ID -> trainingSchedule.id = value as String
                SQLContract.TrainingSchedules.TRAINER -> trainingSchedule.trainer = Query.getInstance().getUserById(value as String)
                SQLContract.TrainingSchedules.ATHLETE -> trainingSchedule.athlete = Query.getInstance().getUserById(value as String)
                SQLContract.TrainingSchedules.STARTDATE -> trainingSchedule.startDate = Date(value as Long)
                else -> Log.w("TrainingSchedule", "$key: $value non appartiene a ${this.javaClass.simpleName}!")
            }
        }
        return trainingSchedule
    }

    fun days(): Int {
        val exercise = exercises.maxBy { exercise -> exercise.day }

        return exercise?.day ?: 0
    }

    fun getDay(day: Int): List<TrainingExercise> {
        return exercises.filter { exercise -> exercise.day == day }
    }
}