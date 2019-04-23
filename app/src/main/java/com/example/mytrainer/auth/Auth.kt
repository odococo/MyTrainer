package com.example.mytrainer.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.mytrainer.GeneralActivity
import com.example.mytrainer.MainActivity
import com.example.mytrainer.LoginActivity
import com.example.mytrainer.component.User
import com.example.mytrainer.database.remote.Firestore
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.example.mytrainer.database.locale.Query as localDB
import com.example.mytrainer.database.remote.Query as remoteDB


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
        if (!isLogged() && !(context is LoginActivity)) {
            toLogin()
        }
    }
    //TODO controllare se l'utente già presente
    fun logged() {
        Firestore.get<User>("users", getId()) { user ->
            localDB.getInstance(context).addUser(user)
            Log.d(TAG, "Aggiunto utente $user")
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
        remoteDB.addUser(user)
        to(MainActivity())
    }

    fun toLogin() {
        to(LoginActivity())
    }
}