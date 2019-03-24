package com.example.mytrainer.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.mytrainer.HomeActivity
import org.telegram.passport.PassportScope
import org.telegram.passport.PassportScopeElementOneOfSeveral
import org.telegram.passport.TelegramLoginButton
import org.telegram.passport.TelegramPassport

class Telegram(
    context: Activity,
    button: TelegramLoginButton,
    TAG: String = "Telegram"
) : Auth(context, TAG) {

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
            TelegramPassport.request(context, request, Codes.TELEGRAM_SIGN_IN.code)
        }
    }

    override fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
        println("Risultato $resultCode e serve ${Activity.RESULT_OK}")
        if (resultCode == Activity.RESULT_OK) {
            to(HomeActivity::class.java)
        } else {
            Log.e(TAG, "Error telegram login!")
            Toast.makeText(context, "Telegram sign in failed:(", Toast.LENGTH_LONG).show()
        }
    }
}