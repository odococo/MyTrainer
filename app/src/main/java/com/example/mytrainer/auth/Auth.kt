package com.example.mytrainer.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.example.mytrainer.HomeActivity
import com.example.mytrainer.LoginActivity
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth


open class Auth(
    val context: Activity,
    val TAG: String = "Auth"
) {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun checkLogin() {
        if (firebaseAuth.currentUser == null && !(context is LoginActivity)) {
            toLogin()
        }
    }

    open fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    fun logout() {
        firebaseAuth.signOut()
        LoginManager.getInstance().logOut()
        Log.d("Auth", "Logout con successo")
        toLogin()
    }

    fun toHome() {
        val intent = Intent(context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    private fun toLogin() {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
}