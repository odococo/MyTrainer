package com.example.mytrainer.component

data class TrainingSchedule(
    var trainer: User,
    var exercises: List<TrainingExercise>
): Component() {

    constructor() : this(User("", ""), listOf(TrainingExercise()))

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "trainer" to trainer,
        "exercises" to exercises
    )
}