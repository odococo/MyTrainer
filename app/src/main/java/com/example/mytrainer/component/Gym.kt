package com.example.mytrainer.component

class Gym(
    var name: String,
    var address: String
): Component() {

    constructor(): this("", "")

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "name" to name,
        "address" to address
    )
}