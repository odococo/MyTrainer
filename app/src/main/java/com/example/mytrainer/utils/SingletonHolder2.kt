package com.example.mytrainer.utils

open class SingletonHolder2<out T, in A, in B>(creator: (A, B) -> T) {
    private var creator: ((A, B) -> T)? = creator
    @Volatile
    private var instance: T? = null

    fun getInstance(): T {
        return instance ?: throw RuntimeException("Crea prima l'istanza con i parametri!")
    }

    fun getInstance(arg1: A, arg2: B): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            instance ?: newInstance(arg1, arg2)
        }
    }

    fun newInstance(arg1: A, arg2: B): T {
        val created = creator!!(arg1, arg2)
        instance = created
        return created
    }
}