package com.example.mytrainer.database.locale

class SQLContract {

    companion object {
        const val ID:String = "id"

        const val USERS_TABLE: String = "users"
        const val USER_EMAIL: String = "email"
        const val USER_PSWD: String = "password"
        const val USER_FIRSTNAME: String = "first_name"
        const val USER_LASTNAME: String = "last_name"
        const val USER_BIRTHDATE: String = "birth_date"

        const val TRAINING_EXERCISES_TABLE: String = "training_exercises"
        const val EXERCISE_SERIES: String = "series"
        const val EXERCISE_REPS: String = "reps"
        const val EXERCISE_RECOVERYTIME: String = "recovery_time"
    }
}