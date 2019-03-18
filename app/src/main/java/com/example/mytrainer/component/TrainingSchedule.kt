package com.example.mytrainer.component

class TrainingSchedule(
    var trainer: Instructor,
    var exercises: List<TrainingExercise>
): Component() {

    constructor(): this(Instructor(), emptyList())

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "trainer" to trainer,
        "exercises" to exercises
    )
}