package com.example.mytrainer.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.facebook.AccessToken



class Facebook(
    context: Activity,
    button: LoginButton,
    TAG: String = "FacebookAuth"
): Auth(context, TAG) {
    private val callbackManager: CallbackManager

    init {
        FacebookSdk.sdkInitialize(context)
        AppEventsLogger.activateApp(context)
        callbackManager = CallbackManager.Factory.create()

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
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "Facebook sign in successful")
                    logged()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e(TAG, "Error facebook login!", task.exception)
                    Toast.makeText(context, "Facebook sign in failer:(", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun isLoggedd(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null && !accessToken.isExpired
    }
}