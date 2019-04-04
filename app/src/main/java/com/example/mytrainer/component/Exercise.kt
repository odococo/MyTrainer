package com.example.mytrainer.component

// rimosso image ByteArray perche' firebase non lo supporta.
open class Exercise(
    var description: String,
    var type: List<String>
): Component() {

    constructor() : this("", listOf(""))

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "description" to description,
        "type" to type
    )
}