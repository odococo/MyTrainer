package com.example.mytrainer.component

import android.util.Log

data class TrainingSchedule(
    var trainer: User,
    var athlete: User,
    var exercises: List<TrainingExercise>
): Component() {

    constructor() : this(User(), User(), listOf(TrainingExercise()))

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "trainer" to trainer,
        "athlete" to athlete,
        "exercises" to exercises
    )

    override fun fromMap(map: Map<String, Any?>): TrainingSchedule {
        val trainingSchedule = TrainingSchedule()
        map.forEach { key, value ->
            when (key) {
                "id" -> trainingSchedule.id = value as String
                "trainer" -> trainingSchedule.trainer = value as User
                "athlete" -> trainingSchedule.athlete = value as User
                "exercises" -> trainingSchedule.exercises = value as List<TrainingExercise>
                else -> Log.w(TAG, "$key: $value non appartiene a ${this.javaClass.simpleName}!")
            }
        }
        return trainingSchedule
    }
}