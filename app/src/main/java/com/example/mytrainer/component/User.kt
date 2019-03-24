package com.example.mytrainer.component

import android.os.Build
import android.support.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

open class User(
    var firstName: String,
    var lastName: String,
    var dateOfBirth: LocalDate
): Component() {

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(): this("", "", LocalDate.parse("1970-01-01", DateTimeFormatter.ISO_DATE))

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "firstName" to firstName,
        "lastName" to lastName,
        "dateOfBirth" to dateOfBirth
    )
}