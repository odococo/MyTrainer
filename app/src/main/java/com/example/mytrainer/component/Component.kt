package com.example.mytrainer.component


abstract class Component(
    var id: String = ""
) {
    // il setter e' Unit e non permette valori di ritorno
    abstract fun withId(id: String): Component

    abstract fun toMap(): Map<String, Any>

    // sarebbe meglio statica ma non si potrebbe ereditare
    abstract fun fromMap(map: Map<String, Any?>): Component

    override fun toString(): String {
        val map = toMap() as MutableMap
        map["id"] = id
        return map.toString()
    }
}