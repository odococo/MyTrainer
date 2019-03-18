package com.example.mytrainer.component

class TrainingExercise(
    var series: Int,
    var reps: Int,
    var recoveryTime: Int
): Exercise() {

    constructor(): this(0, 0, 0)

    override fun toMap(): MutableMap<String, Any> {
        var map = mutableMapOf<String, Any>(
            "series" to series,
            "reps" to reps,
            "recoveryTime" to recoveryTime
        )
        map.putAll(super.toMap())
        return map
    }
}