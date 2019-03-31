package com.example.mytrainer.database.remote

import android.util.Log
import com.example.mytrainer.component.User

object Query {
    private val TAG = "QueryFirestore"

    fun addUser(user: User) {
        Firestore.create("users", user) { ok, info ->
            if (ok) Log.d(TAG, "Utente $info aggiunto con successo")
            else Log.w(TAG, "Utente $user non aggiunto con successo")
        }
    }
}