package com.example.mytrainer.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.mytrainer.component.User
import com.example.mytrainer.utils.SingletonHolder2
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider


class Facebook
private constructor(
    context: Context,
    button: LoginButton
) : Auth(context) {
    companion object : SingletonHolder2<Facebook, Context, LoginButton>(::Facebook)

    private val TAG: String = "FacebookAuth"
    private val callbackManager: CallbackManager = CallbackManager.Factory.create()

    init {

        button.setReadPermissions("email")
        button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Log.d(TAG, "Facebook success")
                firebaseAuth(result!!.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "Facebook cancel")
            }

            override fun onError(error: FacebookException?) {
                Log.w(TAG, "Facebook error", error)
            }
        })
    }

    override fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun firebaseAuth(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "Facebook sign in successful")
                    val firstname = Profile.getCurrentProfile().firstName
                    val lastname = Profile.getCurrentProfile().lastName
                    val user = User(firstname, lastname)
                    user.id = getId()
                    logged(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e(TAG, "Error facebook login!", task.exception)
                    Toast.makeText(context, "Facebook sign in failer:(", Toast.LENGTH_SHORT).show()
                }
            }
    }
}