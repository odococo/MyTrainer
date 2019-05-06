package com.example.mytrainer.database.locale

import com.example.mytrainer.component.*

object SQLContract {
    val DATABASE_NAME = "SQLiteDB"
    val DATABASE_VERSION = 1

    object Users {
        const val NAME = "users"
        const val ID = "id"
        const val FIRSTNAME = "firstname"
        const val LASTNAME = "lastname"
        const val TYPE = "type"
    }

    object Exercises {
        const val NAME = "exercises"
        const val ID = "id"
        const val DESCRIPTION = "description"
    }

    object ExerciseTypes {
        const val NAME = "exercise_types"
        const val ID = "exercise_id"
        const val TYPE = "type"
    }

    object TrainingExercises {
        const val NAME = "training_exercises"
        const val EXERCISE = "exercise_id"
        const val SCHEDULE = "schedule_id"
        const val DAY = "day"
        const val SERIES = "series"
        const val REPS = "reps"
        const val RECOVERYTIME = "recovery_time"
    }

    object TrainingSchedules {
        const val NAME = "training_schedules"
        const val ID = "id"
        const val TRAINER = "trainer_id"
        const val ATHLETE = "athlete_id"
        const val STARTDATE = "start_date"
    }
}