package com.example.mytrainer.component

// rimosso image ByteArray perche' firebase non lo supporta.
open class Exercise(
    var description: String,
    var types: List<String> = emptyList()
): Component() {

    constructor() : this("", listOf(""))

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "description" to description,
        "types" to types
    )
}