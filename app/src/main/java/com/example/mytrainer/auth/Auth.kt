package com.example.mytrainer.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.example.mytrainer.GeneralActivity
import com.example.mytrainer.LoginActivity
import com.example.mytrainer.MainActivity
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth


open class Auth(
    val context: Activity,
    val TAG: String = "Auth"
) {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun checkLogin() {
        if (firebaseAuth.currentUser == null && !(context is LoginActivity)) {
            to(MainActivity::class.java)
        }
    }

    open fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    fun logout() {
        firebaseAuth.signOut()
        LoginManager.getInstance().logOut()
        Log.d("Auth", "Logout con successo")
        to(LoginActivity::class.java)
    }

    fun to(cls: Class<GeneralActivity>) {
        val intent = Intent(context, cls)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
}