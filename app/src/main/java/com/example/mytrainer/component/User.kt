package com.example.mytrainer.component

import android.os.Build
import android.support.annotation.RequiresApi

open class User(
    var firstName: String,
    var lastName: String
): Component() {

    @RequiresApi(Build.VERSION_CODES.O)
    constructor() : this("", "")

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "firstName" to firstName,
        "lastName" to lastName
    )
}