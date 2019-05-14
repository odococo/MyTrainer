package com.example.mytrainer.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.mytrainer.Codes
import com.example.mytrainer.MainActivity
import org.telegram.passport.PassportScope
import org.telegram.passport.PassportScopeElementOneOfSeveral
import org.telegram.passport.TelegramLoginButton
import org.telegram.passport.TelegramPassport

class Telegram(
    context: Activity,
    button: TelegramLoginButton
) : Auth(context) {

    private val TAG: String = "Telegram"

    init {
        button.setOnClickListener {
            val request = TelegramPassport.AuthRequest()
            request.botID = 262354959
            request.publicKey = context.resources.getString(com.example.mytrainer.R.string.telegram_public_key)
            request.nonce = "MyTrainer"
            request.scope = PassportScope(
                PassportScopeElementOneOfSeveral(PassportScope.PASSPORT, PassportScope.IDENTITY_CARD).withSelfie(),
                PassportScope.DRIVER_LICENSE,
                PassportScope.ADDRESS,
                PassportScope.ADDRESS_DOCUMENT,
                PassportScope.PHONE_NUMBER
            )
            TelegramPassport.request(context, request, Codes.SignIn.TELEGRAM)
        }
    }

    override fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
        println("Risultato $resultCode e serve ${Activity.RESULT_OK}")
        if (resultCode == Activity.RESULT_OK) {
            to(MainActivity::class.java)
        } else {
            Log.e(TAG, "Error telegram login!")
            Toast.makeText(context, "Telegram sign in failed:(", Toast.LENGTH_LONG).show()
        }
    }
}