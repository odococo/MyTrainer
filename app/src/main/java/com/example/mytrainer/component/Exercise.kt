package com.example.mytrainer.component

open class Exercise(
    var description: String,
    var type: String,
    var image: ByteArray = ByteArray(0)
): Component() {

    constructor(): this("", "")

    override fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "description" to description,
        "type" to type,
        "image" to image
    )
}