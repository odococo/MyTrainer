package com.example.mytrainer.database.remote

object FirebaseContract {

    object TrainingExercises {
        const val NAME = "trainingExercises"
        const val EXERCISE = "id"
        const val DAY = "day"
        const val SERIES = "series"
        const val REPS = "reps"
        const val RECOVERYTIME = "recoveryTime"
    }

    object Exercises {
        const val NAME = "exercises"
        const val ID = "id"
        const val DESCRIPTION = "description"
        const val TYPES = "types"
    }

    object TrainingSchedules {
        const val NAME = "trainingSchedules"
        const val ID = "id"
        const val TRAINER = "trainer"
        const val ATHLETE = "athlete"
        const val STARTDATE = "startDate"
        const val EXERCISES = "exercises"
    }

    object Users {
        const val NAME = "users"
        const val ID = "id"
        const val FIRSTNAME = "firstName"
        const val LASTNAME = "lastName"
        const val TYPE = "type"
    }
}