package com.example.mytrainer.database.locale

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.mytrainer.auth.Auth
import com.example.mytrainer.component.Component
import com.example.mytrainer.component.Exercise
import com.example.mytrainer.component.User
import com.example.mytrainer.database.SQLContract
import com.example.mytrainer.database.remote.Firestore
import com.example.mytrainer.fragment.login.LoadingFragment
import com.example.mytrainer.utils.SingletonHolder1

class Query
private constructor(private val context: Context) {
    private val TAG = "QuerySQLite"
    private val db: DataBaseOpenHelper = DataBaseOpenHelper.getInstance(context)

    companion object : SingletonHolder1<Query, Context>(::Query)

    fun init(loading: LoadingFragment) {
        if (db.isEmpty("exercises")) {
            Firestore.getAll<Exercise>(SQLContract.getTableName(Exercise())) { exercises ->
                exercises.forEach { exercise ->
                    addExercise(exercise)
                }
                Log.d(TAG, "Aggiunti ${exercises.size} esercizi!")
                // TODO getAll athlete for instructor
                // TODO getAll schedule for both
                loading.joke()
            }
        } else {
            loading.joke()
        }
    }

    private fun objToContentValues(obj: Component): ContentValues {
        val values = ContentValues()
        obj.toMap().forEach { entry ->
            val value = entry.value
            // c'e' bisogno del cast
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
                db.insert("${SQLContract.getTableName(obj)}_$key", values)
            }
        }
    }

    fun clearAndRestoreDB() {
        Log.e(TAG, "Pulendo il db locale!")
        db.onUpgrade(db.writableDatabase, 0, SQLContract.DATABASE_VERSION)
    }

    private fun getComponent(component: Component): Component {
        val map = db.selectOneByKey(SQLContract.getTableName(component), "id", component.id) as MutableMap
        component.toMap().forEach { key, value ->
            if (value is List<*>) {
                val listName = "${SQLContract.getTableName(Exercise())}_$key"
                map[key] = db.selectByKey(listName, "id", component.id).map { entry -> entry[key] as String }
            }
        }
        return component.fromMap(map)
    }

    // solo in locale
    fun addExercise(exercise: Exercise): Boolean {
        return db.insert(SQLContract.getTableName(exercise), objToContentValues(exercise))
    }

    fun getExercise(name: String): Exercise {
        val exercise = Exercise()
        exercise.id = name
        return getComponent(exercise) as Exercise
    }

    // solo in locale
    fun addUser(user: User): Boolean {
        return db.insert(SQLContract.getTableName(user), objToContentValues(user), conflict = true)
    }

    fun getUser(): User {
        return getUserById(Auth.getId())
    }

    fun getUserById(id: String): User {
        val user = User()
        user.id = id
        return getComponent(user) as User
    }

    fun addTrainingSchedule() {

    }
}