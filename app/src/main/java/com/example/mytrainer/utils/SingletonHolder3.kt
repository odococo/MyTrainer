package com.example.mytrainer.utils

open class SingletonHolder3<out T, in A, in B, in C>(creator: (A, B, C) -> T) {
    private var creator: ((A, B, C) -> T)? = creator
    @Volatile
    private var instance: T? = null

    fun getInstance(): T {
        val i = instance ?: throw RuntimeException("Crea prima l'istanza con i parametri!")
        return i
    }

    fun getInstance(arg1: A, arg2: B, arg3: C): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg1, arg2, arg3)
                instance = created
                creator = null
                created
            }
        }
    }
}