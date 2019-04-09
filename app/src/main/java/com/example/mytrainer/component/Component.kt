package com.example.mytrainer.component

abstract class Component(
    var id: String = "")
{
    protected val TAG = "Component"

    abstract fun toMap(): Map<String, Any>

    abstract fun fromMap(map: Map<String, Any?>): Component

    override fun toString(): String {
        val map = toMap() as MutableMap
        map["id"] = id
        return map.toString()
    }
}