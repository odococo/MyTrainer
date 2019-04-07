package com.example.mytrainer.database.remote

import android.util.Log
import com.example.mytrainer.component.Exercise
import com.example.mytrainer.component.User
import com.example.mytrainer.database.SQLContract

object Query {
    private val TAG = "QueryFirestore"

    fun addUser(user: User) {
        Firestore.create(SQLContract.getTableName(user), user) { ok, info ->
            if (ok) Log.d(TAG, "Utente $info aggiunto con successo")
            else Log.w(TAG, "Utente $user non aggiunto con successo")
        }
    }

    fun addExercise(exercise: Exercise) {
        Firestore.create(SQLContract.getTableName(exercise), exercise) { ok, info ->
            if (ok) Log.d(TAG, "Aggiunto esercizio: ${exercise.id}")
            else Log.w(TAG, "Errore aggiunta esercizio ${exercise.id}: $info")
        }
    }

    fun getAllExercises(callback: (List<Exercise>) -> Unit) {
        return Firestore.getAll("exercises", callback)
    }
}