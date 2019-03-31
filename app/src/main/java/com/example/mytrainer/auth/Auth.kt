package com.example.mytrainer.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.example.mytrainer.GeneralActivity
import com.example.mytrainer.HomeActivity
import com.example.mytrainer.MainActivity
import com.example.mytrainer.component.User
import com.example.mytrainer.database.remote.Firestore
import com.example.mytrainer.database.remote.Query
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth


open class Auth(
    val context: Activity,
    val TAG: String = "Auth"
) {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun isLogged(): Boolean {
        println(firebaseAuth.currentUser?.uid)
        return firebaseAuth.currentUser != null
    }

    fun checkLogin() {
        if (!isLogged() && !(context is MainActivity)) {
            toLogin()
        }
    }

    fun getId(): String {
        return firebaseAuth.currentUser?.uid!!
    }

    fun logged() {
        Firestore.get<User>("users", getId()) { user ->
            toHome(user)
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

    fun to(activity: GeneralActivity) {
        val intent = Intent(context, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    fun toHome(user: User) {
        Query.addUser(user)
        to(HomeActivity())
    }

    fun toLogin() {
        to(MainActivity())
    }
}