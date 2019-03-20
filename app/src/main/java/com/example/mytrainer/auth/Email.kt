package com.example.mytrainer.auth

import android.util.Log
import com.example.mytrainer.component.DBUser
import com.example.mytrainer.database.remote.Firestore
import com.google.firebase.auth.FirebaseAuth

object Email {

    fun create(
        user: DBUser,
        callback: (ok: Boolean, result: Any) -> Unit
    ) {
        val tag = "Create-${user.email}"
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener { result ->
                Log.d(tag, "Ok auth")
                Firestore.create("utenti", user) { ok, data ->
                    println("$ok $data")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(tag, "Errore auth", exception)
            }
    }

    fun verifyEmail() {
        val auth = FirebaseAuth.getInstance()
        auth.currentUser!!.
            .sendEmailVerification()
    }

    fun login(user: DBUser) {
        val tag = "Login-${user.email}"
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener { result ->
                println(result.additionalUserInfo.isNewUser)
                println(result.user.isEmailVerified)
                println(result)
                Log.d(tag, "Login")
            }
            .addOnFailureListener { exception ->
                Log.w(tag, "Login fallito", exception)
            }
    }

    fun logout() {
        val tag = "Logout"
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        Log.d(tag, "Logout con successo")
    }
}