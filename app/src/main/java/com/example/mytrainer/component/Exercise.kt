package com.example.mytrainer.component

open class Exercise(
    var name: String,
    var description: String
): Component() {

    constructor(): this("", "")

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "name" to name,
        "description" to description
    )
}