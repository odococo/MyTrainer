package com.example.mytrainer.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.mytrainer.*
import com.example.mytrainer.component.User
import com.example.mytrainer.utils.SingletonHolder1
import com.google.firebase.auth.FirebaseAuth
import com.example.mytrainer.database.locale.Query as localDB
import com.example.mytrainer.database.remote.Query as remoteDB

// singleton puro non possibile in quanto il primo context sarebbe quello di EmptyActivity
open class Auth(
    val context: Context
) {
    private val TAG: String = "Auth"
    private var user: User = User()
    private lateinit var successfulLogin: () -> Unit
    private lateinit var failedLogin: () -> Unit

    protected val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    companion object : SingletonHolder1<Auth, Context>(::Auth)

    fun logged(user: User = getUser()) {
        remoteDB.addUser(user) { u ->
            localDB.getInstance().addUser(u)
            this.user = u
        }
        Log.d(TAG, "Aggiunto utente $user")
        successfulLogin()
    }

    fun failed() {
        Log.d(TAG, "SignIn fallito!")
        failedLogin()
    }

    open fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    fun isLogged(): Boolean {
        return firebaseAuth.currentUser != null
    }

    fun getId(): String {
        return if (isLogged()) firebaseAuth.currentUser?.uid!! else ""
    }

    fun getUser(): User {
        return localDB.getInstance(context).getUser()
    }

    fun setSuccessfulLogin(callback: () -> Unit) {
        successfulLogin = callback
    }

    fun setFailedLogin(callback: () -> Unit) {
        failedLogin = callback
    }

    fun logout() {
        firebaseAuth.signOut()
        Log.d("Auth", "Logout con successo")
        toLogin()
    }

    private fun to(activity: GeneralActivity) {
        val intent = Intent(context, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    fun toHome() {
        to(MainActivity())
    }

    fun toTrainer() {
        to(TrainerActivity())
    }

    fun toAdmin() {
        to(AdminActivity())
    }

    private fun toLogin() {
        to(LoginActivity())
    }
}