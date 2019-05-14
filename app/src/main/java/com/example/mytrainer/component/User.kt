package com.example.mytrainer.component

import android.util.Log
import com.example.mytrainer.database.locale.SQLContract
import com.example.mytrainer.database.remote.FirebaseContract

open class User(
    var firstName: String,
    var lastName: String,
    var type: String = "athlete"
) : Component() {

    constructor() : this("", "")

    override fun withId(id: String): User {
        this.id = id

        return this
    }

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        FirebaseContract.Users.FIRSTNAME to firstName,
        FirebaseContract.Users.LASTNAME to lastName,
        FirebaseContract.Users.TYPE to type
    )

    override fun fromMap(map: Map<String, Any?>): User {
        val user = User()
        map.forEach { (key, value) ->
            when (key) {
                SQLContract.Users.ID -> user.id = value as String
                SQLContract.Users.FIRSTNAME -> user.firstName = value as String
                SQLContract.Users.LASTNAME -> user.lastName = value as String
                SQLContract.Users.TYPE -> user.type = value as String
                else -> Log.w("User", "$key: $value non appartiene a ${this.javaClass.simpleName}!")
            }
        }
        return user
    }

    fun name(): String {
        return "$firstName $lastName"
    }
}