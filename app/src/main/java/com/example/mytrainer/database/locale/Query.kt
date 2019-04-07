package com.example.mytrainer.database.locale

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.mytrainer.component.Component
import com.example.mytrainer.component.Exercise
import com.example.mytrainer.database.SQLContract
import com.example.mytrainer.utils.SingletonHolder1

class Query
private constructor(context: Context) {
    private val TAG = "QuerySQLite"
    private val db: DataBaseOpenHelper = DataBaseOpenHelper.getInstance(context)

    companion object : SingletonHolder1<Query, Context>(::Query)

    private fun objToContentValues(obj: Component): ContentValues {
        val values = ContentValues()
        obj.toMap().forEach { entry ->
            val value = entry.value
            when (value) {
                is String -> values.put(entry.key, value)
                is Byte -> values.put(entry.key, value)
                is Short -> values.put(entry.key, value)
                is Int -> values.put(entry.key, value)
                is Long -> values.put(entry.key, value)
                is Float -> values.put(entry.key, value)
                is Double -> values.put(entry.key, value)
                is Boolean -> values.put(entry.key, value)
                is ByteArray -> values.put(entry.key, value)
                is List<*> -> listToTable(obj, entry.key, entry.value as List<*>)
                else -> Log.w(TAG, "$value e' di un tipo non compatibile con SQLite")
            }
        }
        values.put("id", obj.id)
        return values
    }

    private fun listToTable(obj: Component, key: String, value: List<*>) {
        if (!value.isEmpty() && value[0] !is Component) {
            for (v in value) {
                val values = ContentValues()
                values.put("id", obj.id)
                when (v) {
                    is String -> values.put(key, v)
                    is Byte -> values.put(key, v)
                    is Short -> values.put(key, v)
                    is Int -> values.put(key, v)
                    is Long -> values.put(key, v)
                    is Float -> values.put(key, v)
                    is Double -> values.put(key, v)
                    is Boolean -> values.put(key, v)
                    is ByteArray -> values.put(key, v)
                    else -> Log.w(TAG, "$v e' di un tipo non compatibile con SQLite")
                }
                db.insert("${SQLContract.getTableName(obj)}_type", values)
            }
        }
    }

    fun addExercise(exercise: Exercise) {
        db.insert(SQLContract.getTableName(exercise), objToContentValues(exercise))
    }

    fun getExercise(name: String) {
        val map = db.select(SQLContract.getTableName(Exercise()))
        println(map)
    }
}