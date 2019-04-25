package com.example.mytrainer.component

import android.util.Log

open class User(
    var firstName: String,
    var lastName: String,
    var type: String = "athlete"
) : Component() {

    constructor() : this("", "")

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "firstName" to firstName,
        "lastName" to lastName,
        "type" to type
    )

    override fun fromMap(map: Map<String, Any?>): User {
        val user = User()
        map.forEach { key, value ->
            when (key) {
                "id" -> user.id = value as String
                "firstName" -> user.firstName = value as String
                "lastName" -> user.lastName = value as String
                "type" -> user.type = value as String
                else -> Log.w(TAG, "$key: $value non appartiene a ${this.javaClass.simpleName}!")
            }
        }
        return user
    }
}