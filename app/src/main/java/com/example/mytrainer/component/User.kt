package com.example.mytrainer.component

open class User(
    var firstName: String,
    var lastName: String
): Component() {

    constructor() : this("", "")

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "firstName" to firstName,
        "lastName" to lastName
    )
}