package com.example.mytrainer.database.remote

import android.util.Log
import com.example.mytrainer.component.Exercise
import com.example.mytrainer.component.TrainingSchedule
import com.example.mytrainer.component.User

object Query {
    private val TAG = "QueryFirestore"

    fun addUser(user: User, callback: (User) -> Unit) {
        Firestore.get<User>(FirebaseContract.Users.NAME, user.id) { u ->
            if (u.id.isEmpty()) {
                Firestore.create(FirebaseContract.Users.NAME, user) { ok, info ->
                    if (ok) {
                        Log.d(TAG, "Utente ${(info as User).id} aggiunto con successo")
                        callback(info)
                    } else Log.w(TAG, "Utente $user non aggiunto con successo")
                }
            } else {
                callback(u)
            }
        }
    }

    fun addExercise(exercise: Exercise) {
        Firestore.create(FirebaseContract.Exercises.NAME, exercise) { ok, info ->
            if (ok) Log.d(TAG, "Aggiunto esercizio: ${exercise.id}")
            else Log.w(TAG, "Errore aggiunta esercizio ${exercise.id}: $info")
        }
    }

    fun addTrainingSchedule(schedule: TrainingSchedule, callback: (TrainingSchedule) -> Unit) {
        Firestore.create(FirebaseContract.TrainingSchedules.NAME, schedule) { ok, info ->
            if (ok) {
                Log.d(TAG, "Aggiunta scheda: ${(info as TrainingSchedule).id}")
                callback(info)
            } else Log.w(TAG, "Errore aggiunta scheda $schedule: $info")
        }
    }

    fun getAllExercises(callback: (List<Exercise>) -> Unit) {
        Firestore.getAll(FirebaseContract.Exercises.NAME, callback)
    }

    fun getAllSchedules(user: User, callback: (List<TrainingSchedule>) -> Unit) {
        getAllAthleteSchedules(user) { athletes ->
            getAllTrainerSchedules(user) { trainers ->
                callback(athletes + trainers)
            }
        }
    }

    private fun getAllAthleteSchedules(user: User, callback: (List<TrainingSchedule>) -> Unit) {
        Firestore.find(
            FirebaseContract.TrainingSchedules.NAME,
            "${FirebaseContract.TrainingSchedules.ATHLETE}.${FirebaseContract.Users.ID}", user.id,
            callback
        )
    }

    private fun getAllTrainerSchedules(user: User, callback: (List<TrainingSchedule>) -> Unit) {
        Firestore.find(
            FirebaseContract.TrainingSchedules.NAME,
            "${FirebaseContract.TrainingSchedules.TRAINER}.${FirebaseContract.Users.ID}", user.id,
            callback
        )
    }

    fun getAllUsers(callback: (List<User>) -> Unit) {
        Firestore.getAll(FirebaseContract.Users.NAME, callback)
    }
}