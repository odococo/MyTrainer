package com.example.mytrainer.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.mytrainer.EmptyActivity
import com.example.mytrainer.GeneralActivity
import com.example.mytrainer.LoginActivity
import com.example.mytrainer.MainActivity
import com.example.mytrainer.component.User
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.example.mytrainer.database.locale.Query as localDB
import com.example.mytrainer.database.remote.Query as remoteDB


open class Auth(
    val context: Context
) {
    private val TAG: String = "Auth"
    private var user: User = User()
    private lateinit var successfulLogin: (() -> Unit)

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
        if (!isLogged() && context !is LoginActivity) {
            toLogin()
        } else if (isLogged() && context is EmptyActivity) {
            // gia' loggato. Recupero le info localmente
            user = localDB.getInstance(context).getUser()
            if (user.id.isNotEmpty()) {
                toHome()
            } else {
                Log.w(TAG, "Utente loggato ma non presente nel database locale")
                logout()
                toLogin()
            }
        }
    }

    fun logged(user: User) {
        this.user = user
        remoteDB.addUser(user)
        localDB.getInstance().addUser(user)
        Log.d(TAG, "Aggiunto utente $user")
        successfulLogin()
    }


    open fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    private fun isLogged(): Boolean {
        return Companion.isLogged()
    }

    fun getId(): String {
        return Companion.getId()
    }

    fun getUser(): User {
        return user
    }

    fun setSuccessfulLogin(callback: (() -> Unit)) {
        successfulLogin = callback
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

    fun toHome() {
        to(MainActivity())
    }

    fun toLogin() {
        to(LoginActivity())
    }
}