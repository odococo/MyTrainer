package com.example.mytrainer.component

import android.util.Log
import com.example.mytrainer.database.locale.SQLContract
import com.example.mytrainer.database.remote.FirebaseContract

// rimosso image ByteArray perche' firebase non lo supporta.
open class Exercise(
    var description: String,
    var types: List<String> = emptyList()
) : Component() {

    constructor() : this("", listOf(""))

    override fun withId(id: String): Exercise {
        this.id = id

        return this
    }

    override fun toMap(): Map<String, Any> = mutableMapOf(
        FirebaseContract.Exercises.DESCRIPTION to description,
        FirebaseContract.Exercises.TYPES to types
    )

    override fun fromMap(map: Map<String, Any?>): Exercise {
        val exercise = Exercise()
        map.forEach { (key, value) ->
            when (key) {
                SQLContract.Exercises.ID -> exercise.id = value as String
                SQLContract.Exercises.DESCRIPTION -> exercise.description = value as String
                else -> Log.w("Exercise", "$key: $value non appartiene a ${this.javaClass.simpleName}!")
            }
        }
        return exercise
    }
}