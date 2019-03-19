package com.example.mytrainer.component


data class DBUser(var email: String, var password: String): Component() {

    constructor(): this("", "")

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "email" to email,
        "password" to password
    )
}