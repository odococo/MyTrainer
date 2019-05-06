package com.example.mytrainer.database.remote

import com.example.mytrainer.component.*

object FirebaseContract {

    fun getTableName(obj: Component) = when (obj) {
        is TrainingExercise -> TrainingExercises.NAME
        is Exercise -> Exercises.NAME
        is TrainingSchedule -> TrainingSchedules.NAME
        is User -> Users.NAME
        else -> throw IllegalArgumentException("L'oggetto non ha una tabella correlata")
    }

    object TrainingExercises {
        const val NAME = "trainingExercises"
        const val DAY = "day"
        const val SERIES = "series"
        const val REPS = "reps"
        const val RECOVERYTIME = "recoveryTime"
    }

    object Exercises {
        const val NAME = "exercises"
        const val DESCRIPTION = "description"
        const val TYPES = "types"
    }

    object TrainingSchedules {
        const val NAME = "trainingSchedules"
        const val TRAINER = "trainer"
        const val ATHLETE = "athlete"
        const val STARTDATE = "startDate"
        const val EXERCISES = "exercises"
    }

    object Users {
        const val NAME = "users"
        const val FIRSTNAME = "firstName"
        const val LASTNAME = "lastName"
        const val TYPE = "type"
    }
}