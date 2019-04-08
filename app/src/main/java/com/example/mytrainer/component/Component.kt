package com.example.mytrainer.component

abstract class Component(
    var id: String = "")
{
    abstract fun toMap(): MutableMap<String, Any>

    override fun toString(): String {
        val map = toMap()
        map["id"] = id
        return map.toString()
    }
}