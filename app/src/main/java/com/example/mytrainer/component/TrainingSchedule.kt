package com.example.mytrainer.component

import android.util.Log
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
        "trainer" to trainer,
        "athlete" to athlete,
        "startDate" to startDate,
        "exercises" to exercises
    )

    override fun fromMap(map: Map<String, Any?>): TrainingSchedule {
        val trainingSchedule = TrainingSchedule()
        map.forEach { (key, value) ->
            when (key) {
                "id" -> trainingSchedule.id = value as String
                "trainer" -> trainingSchedule.trainer = value as User
                "athlete" -> trainingSchedule.athlete = value as User
                "startDate" -> trainingSchedule.startDate = Date(value as Long)
                "exercises" -> trainingSchedule.exercises = value as List<TrainingExercise>
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