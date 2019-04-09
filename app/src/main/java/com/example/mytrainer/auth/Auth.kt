package com.example.mytrainer.auth

import android.content.Context
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
    val context: Context
) {
    private val TAG: String = "Auth"

    protected val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // alcune classi potrebbero aver bisogno dell'id senza pero' avere un context
    companion object {
        private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

        fun isLogged(): Boolean {
            return firebaseAuth.currentUser != null
        }

        fun getId(): String {
            return if (isLogged()) firebaseAuth.currentUser?.uid!! else ""
        }

        fun logout() {
            firebaseAuth.signOut()
            LoginManager.getInstance().logOut()
            Log.d("Auth", "Logout con successo")
        }
    }

    fun checkLogin() {
        if (!isLogged() && !(context is MainActivity)) {
            toLogin()
        }
    }

    fun logged() {
        Firestore.get<User>("users", getId()) { user ->
            toHome(user)
        }
    }

    open fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    fun isLogged(): Boolean {
        return Companion.isLogged()
    }

    fun getId(): String {
        return Companion.getId()
    }

    fun logout() {
        Companion.logout()
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