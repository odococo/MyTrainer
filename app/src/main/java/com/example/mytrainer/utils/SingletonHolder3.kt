package com.example.mytrainer.utils

open class SingletonHolder3<out T, in A, in B, in C>(creator: (A, B, C) -> T) {
    private var creator: ((A, B, C) -> T)? = creator
    @Volatile
    private var instance: T? = null

    fun getInstance(): T {
        return instance ?: throw RuntimeException("Crea prima l'istanza con i parametri!")
    }

    fun getInstance(arg1: A, arg2: B, arg3: C): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            instance ?: newInstance(arg1, arg2, arg3)
        }
    }

    fun newInstance(arg1: A, arg2: B, arg3: C): T {
        val created = creator!!(arg1, arg2, arg3)
        instance = created
        return created
    }
}