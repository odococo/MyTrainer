package com.example.mytrainer.utils

open class SingletonHolder<T>(creator: () -> T) {
    private var creator: (() -> T)? = creator
    @Volatile
    private var instance: T? = null


    fun getInstance(): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            instance ?: newInstance()
        }
    }

    fun newInstance(): T {
        val created = creator!!()
        instance = created
        return created
    }
}