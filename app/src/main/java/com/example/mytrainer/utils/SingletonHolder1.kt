package com.example.mytrainer.utils

open class SingletonHolder1<out T, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile
    private var instance: T? = null

    fun getInstance(): T {
        return instance ?: throw RuntimeException("Crea prima l'istanza con i parametri!")
    }

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            instance ?: newInstance(arg)
        }
    }

    fun newInstance(arg: A): T {
        val created = creator!!(arg)
        instance = created
        return created
    }
}