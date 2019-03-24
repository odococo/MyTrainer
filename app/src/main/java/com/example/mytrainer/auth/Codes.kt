package com.example.mytrainer.auth

enum class Codes(val code: Int) {
    GOOGLE_SIGN_IN(1),
    FACEBOOK_SIGN_IN(64206),
    TELEGRAM_SIGN_IN(352);

    companion object {
        private val map = Codes.values().associateBy(Codes::code)

        fun fromInt(type: Int) = map[type]
    }
}