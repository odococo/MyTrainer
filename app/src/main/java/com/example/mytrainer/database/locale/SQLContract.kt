package com.example.mytrainer.database.locale

class SQLContract {

    companion object {
        const val ID:String = "id"  //Primary Key of all tables

        const val USERS: String = "users"
        const val EMAIL: String = "email"
        const val PASSWORD: String = "password"
        const val FIRST_NAME: String = "first_name"
        const val LAST_NAME: String = "last_name"
        const val USER_TYPE: String = "user_type"
        const val BIRTH_DATE: String = "birth_date"

        const val TRAINING_SCHEDULES: String = "training_schedule"
        const val OBJECTIVE: String = "objective"
        const val DESCRIPTION: String = "description"
        const val CREATE_DATE: String = "create_date"
        const val END_DATE: String = "end_date"
        const val ATHLET_ID: String = "athlet_id"   //Foreign Key
        const val INSTRUCTOR_ID: String = "instructor_id"   //Foreign Key

        const val TRAINING_EXERCISES: String = "training_exercises"
        const val SERIES: String = "series"
        const val REPS: String = "reps"
        const val RECOVERY_TIME: String = "recovery_time"
        const val SCHEDULE_ID: String = "schedule_id"   //Foreign Key
        const val EXERCISE_ID: String = "execrise_id"  //Foreign Key


        const val EXERCISES: String = "training_exercises"
        const val NAME: String = "name"
        const val IMMAGE: String = "immage"

    }
    private constructor()
}