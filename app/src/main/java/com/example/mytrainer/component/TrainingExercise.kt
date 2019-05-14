package com.example.mytrainer.component

import android.util.Log
import com.example.mytrainer.database.locale.SQLContract
import com.example.mytrainer.database.remote.FirebaseContract

data class TrainingExercise(
    var day: Int,
    var series: Int,
    var reps: Int,
    var recoveryTime: Int
) : Exercise() {

    constructor() : this(0, 0, 0, 0)

    override fun withId(id: String): TrainingExercise {
        this.id = id

        return this
    }

    // TODO da rivedere perche' in remoto ho la mappa anche di exercise
    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        FirebaseContract.TrainingExercises.DAY to day,
        FirebaseContract.TrainingExercises.SERIES to series,
        FirebaseContract.TrainingExercises.REPS to reps,
        FirebaseContract.TrainingExercises.RECOVERYTIME to recoveryTime
    )

    override fun fromMap(map: Map<String, Any?>): TrainingExercise {
        val trainingExercise = TrainingExercise()
        map.forEach { (key, value) ->
            when (key) {
                SQLContract.TrainingExercises.EXERCISE -> trainingExercise.id = value as String
                SQLContract.TrainingExercises.DAY -> trainingExercise.day = value as Int
                SQLContract.TrainingExercises.SERIES -> trainingExercise.series = value as Int
                SQLContract.TrainingExercises.REPS -> trainingExercise.reps = value as Int
                SQLContract.TrainingExercises.RECOVERYTIME -> trainingExercise.recoveryTime = value as Int
                SQLContract.TrainingExercises.SCHEDULE -> {
                }
                else -> Log.w("TrainingExercise", "$key: $value non appartiene a ${this.javaClass.simpleName}!")
            }
        }
        return trainingExercise
    }

    fun overallWork(): String {
        return "Series $series x $reps reps"
    }

    fun time(): String {
        return "$recoveryTime sec"
    }
}