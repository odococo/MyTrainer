package com.example.mytrainer.component

import android.util.Log

// rimosso image ByteArray perche' firebase non lo supporta.
open class Exercise(
    var description: String,
    var types: List<String> = emptyList()
) : Component() {

    constructor() : this("", listOf(""))

    override fun toMap(): Map<String, Any> = mutableMapOf(
        "description" to description,
        "types" to types
    )

    override fun fromMap(map: Map<String, Any?>): Exercise {
        val exercise = Exercise()
        map.forEach { key, value ->
            when (key) {
                "id" -> exercise.id = value as String
                "description" -> exercise.description = value as String
                "types" -> exercise.types = value as List<String>
                else -> Log.w(TAG, "$key: $value non appartiene a ${this.javaClass.simpleName}!")
            }
        }
        return exercise
    }
}