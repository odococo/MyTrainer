package com.example.mytrainer.database.locale

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import com.example.mytrainer.auth.Auth
import com.example.mytrainer.component.Component
import com.example.mytrainer.component.Exercise
import com.example.mytrainer.component.User
import com.example.mytrainer.database.SQLContract
import com.example.mytrainer.utils.SingletonHolder1

class Query
private constructor(private val activity: Activity) {
    private val TAG = "QuerySQLite"
    private val db: DataBaseOpenHelper = DataBaseOpenHelper.getInstance(activity)

    companion object : SingletonHolder1<Query, Activity>(::Query)

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

    fun clearAndRestoreDB() {
        db.onUpgrade(db.writableDatabase, 0, SQLContract.DATABASE_VERSION)
    }

    fun addExercise(exercise: Exercise): Boolean {
        return db.insert(SQLContract.getTableName(exercise), objToContentValues(exercise))
    }

    // TODO cercare lista di tipi per un esercizio
    fun getExercise(name: String): Exercise {
        val exercise = Exercise()
        val map = db.selectByPK(SQLContract.getTableName(exercise), name)
        exercise.id = map["id"] as String
        exercise.description = map["description"] as String
        return exercise
    }

    fun addUser(user: User): Boolean {
        return db.insert(SQLContract.getTableName(user), objToContentValues(user))
    }

    fun getUser(): User {
        val user = User()
        val map = db.selectByPK(SQLContract.getTableName(user), Auth(activity).getId())
        user.id = map["id"] as String
        user.firstName = map["firstName"] as String
        user.lastName = map["lastName"] as String
        user.type = map["type"] as String
        return user
    }
}