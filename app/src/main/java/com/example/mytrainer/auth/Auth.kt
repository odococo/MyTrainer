package com.example.mytrainer.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.example.mytrainer.MainActivity
import com.google.firebase.auth.FirebaseAuth

abstract class Auth(
    val context: Activity,
    val TAG: String = "Auth"
) {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun logged() {
        if (isLogged()) { // non si sa mai
            context.startActivity(Intent(context, MainActivity::class.java))
        } else {
            Log.w(TAG, "Called logged but user is not logged!")
        }
    }

    abstract fun handleResult(requestCode: Int, resultCode: Int, data: Intent?)

    companion object {

        fun isLogged(): Boolean = FirebaseAuth.getInstance().currentUser != null

        fun logout() {
            FirebaseAuth.getInstance().signOut()
            Log.d("Auth", "Logout con successo")
        }
    }
}