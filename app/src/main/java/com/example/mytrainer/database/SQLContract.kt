package com.example.mytrainer.database

import com.example.mytrainer.component.*

object SQLContract {
    val DATABASE_NAME = "SQLiteDB"
    val DATABASE_VERSION = 1

    fun getTableName(obj: Component) = when (obj) {
        is TrainingExercise -> "trainingExercises"
        is Exercise -> "exercises"
        is TrainingSchedule -> "trainingSchedules"
        is User -> "users"
        else -> throw IllegalArgumentException("L'oggetto non ha una tabella correlata")
    }

    val ID: String = "id"  //Primary Key of all tables

    val USERS: String = "users"
    val EMAIL: String = "email"
    val PASSWORD: String = "password"
    val FIRST_NAME: String = "first_name"
    val LAST_NAME: String = "last_name"
    val USER_TYPE: String = "user_type"
    val BIRTH_DATE: String = "birth_date"

    val TRAINING_SCHEDULES: String = "training_schedule"
    val OBJECTIVE: String = "objective"
    val DESCRIPTION: String = "description"
    val CREATE_DATE: String = "create_date"
    val END_DATE: String = "end_date"
    val ATHLET_ID: String = "athlet_id"   //Foreign Key
    val INSTRUCTOR_ID: String = "instructor_id"   //Foreign Key

    val TRAINING_EXERCISES: String = "training_exercises"
    val SERIES: String = "series"
    val REPS: String = "reps"
    val RECOVERY_TIME: String = "recovery_time"
    val SCHEDULE_ID: String = "schedule_id"   //Foreign Key
    val EXERCISE_ID: String = "execrise_id"  //Foreign Key


    val EXERCISES: String = "training_exercises"
    val NAME: String = "name"
    val IMMAGE: String = "immage"
}