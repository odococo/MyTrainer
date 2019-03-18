package com.example.mytrainer.component

abstract class Component(
    var id: String = "")
{
    abstract fun toMap(): MutableMap<String, Any>
}