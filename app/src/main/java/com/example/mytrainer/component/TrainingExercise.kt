package com.example.mytrainer.component

import android.util.Log

data class TrainingExercise(
    var series: Int,
    var reps: Int,
    var recoveryTime: Int
): Exercise() {

    constructor(): this(0, 0, 0)

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
            "series" to series,
            "reps" to reps,
            "recoveryTime" to recoveryTime
        )

    override fun fromMap(map: Map<String, Any?>): TrainingExercise {
        val trainingExercise = TrainingExercise()
        map.forEach { key, value ->
            when (key) {
                "id" -> trainingExercise.id = value as String
                "series" -> trainingExercise.series = value as Int
                "reps" -> trainingExercise.reps = value as Int
                "recoveryTime" -> trainingExercise.recoveryTime = value as Int
                else -> Log.w(TAG, "$key: $value non appartiene a ${this.javaClass.simpleName}!")
            }
        }
        return trainingExercise
    }
}