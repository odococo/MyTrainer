package com.example.mytrainer.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.mytrainer.HomeActivity
import com.example.mytrainer.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider

class Google(
    context: Activity,
    button: SignInButton,
    TAG: String = "GoogleAuth"
): Auth(context, TAG) {

    private val client: GoogleSignInClient

    init {
        button.setOnClickListener {
            selectAccount()
        }

        // Configure Google Sign In
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        client = GoogleSignIn.getClient(context, googleSignInOptions)
    }

    private fun selectAccount() {
        val intent: Intent = client.signInIntent
        context.startActivityForResult(intent, Codes.GOOGLE_SIGN_IN.code)
    }

    override fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.handleResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuth(account!!)
            } catch (e: ApiException) {
                Log.w(TAG, "API Exception!", e)
                Toast.makeText(context, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        } else {
            Log.e(TAG, "Error google login!")
            Toast.makeText(context, "Google select account failed:(", Toast.LENGTH_LONG).show()
        }

    }

    private fun firebaseAuth(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Google sign in successful!")
                to(HomeActivity::class.java)
            } else {
                Log.e(TAG, "Error google sign in!", task.exception)
                Toast.makeText(context, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

}