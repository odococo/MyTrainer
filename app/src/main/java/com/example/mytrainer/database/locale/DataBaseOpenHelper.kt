package com.example.mytrainer.database.locale

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.mytrainer.component.*
import com.example.mytrainer.database.SQLContract
import com.example.mytrainer.utils.SingletonHolder1
import java.util.*

//Singleton
class DataBaseOpenHelper
private constructor(private val context: Context) :
    SQLiteOpenHelper(context, SQLContract.DATABASE_NAME, null, SQLContract.DATABASE_VERSION) {

    private val TAG: String = "DataBaseOpenHelper"
    private lateinit var db: SQLiteDatabase

    companion object : SingletonHolder1<DataBaseOpenHelper, Context>(::DataBaseOpenHelper)

    init {
        Log.d(TAG, "Init DataBaseOpenHelper")
        if (isEmpty("users")) {
            onUpgrade(db, 0, SQLContract.DATABASE_VERSION) // non lo chiama in automatico
        } else {
            onCreate(db)
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        // per alcune tabelle vengono create insieme piu' tabelle ma execSQL ne esegue una sola alla volta, fino a ;
        val tables = mutableListOf<String>()
        tables.add(
            objToTable(SQLContract.Users.NAME, mapOf(
                SQLContract.Users.ID to "",
                SQLContract.Users.FIRSTNAME to "",
                SQLContract.Users.LASTNAME to "",
                SQLContract.Users.TYPE to "athlete"
            ), arrayOf(SQLContract.Users.ID)))
        tables.add(
            objToTable(SQLContract.Exercises.NAME, mapOf(
                SQLContract.Exercises.ID to "",
                SQLContract.Exercises.DESCRIPTION to ""
            ), arrayOf(SQLContract.Exercises.ID))
        )
        tables.add(
            objToTable(SQLContract.TrainingSchedules.NAME, mapOf(
                SQLContract.TrainingSchedules.ID to "",
                SQLContract.TrainingSchedules.TRAINER to "",
                SQLContract.TrainingSchedules.ATHLETE to "",
                SQLContract.TrainingSchedules.STARTDATE to 0
            ), arrayOf(SQLContract.TrainingSchedules.ID), mapOf(
                SQLContract.TrainingSchedules.TRAINER to SQLContract.Users.NAME,
                SQLContract.TrainingSchedules.ATHLETE to SQLContract.Users.NAME
            ))
        )
        tables.add(
            objToTable(SQLContract.TrainingExercises.NAME, mapOf(
                SQLContract.TrainingExercises.EXERCISE to "",
                SQLContract.TrainingExercises.SCHEDULE to "",
                SQLContract.TrainingExercises.DAY to 0,
                SQLContract.TrainingExercises.SERIES to 0,
                SQLContract.TrainingExercises.REPS to 0,
                SQLContract.TrainingExercises.RECOVERYTIME to 0
            ), arrayOf(SQLContract.TrainingExercises.EXERCISE, SQLContract.TrainingExercises.SCHEDULE), mapOf(
                SQLContract.TrainingExercises.EXERCISE to SQLContract.Exercises.NAME,
                SQLContract.TrainingExercises.SCHEDULE to SQLContract.TrainingSchedules.NAME
            ))
        )
        tables
            .forEach { table ->
                table.split(";").forEach { query ->
                    if (query.isNotEmpty()) { // potrebbe essere stringa vuota, non so come pero'
                        exec(query)
                    }
                }
            }
        Log.d(TAG, "onCreate new DataBase")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        select("sqlite_master", arrayOf("name"), "name NOT LIKE ?", arrayOf("sqlite%"), limit = -1).forEach { entry ->
            exec("DROP TABLE ${entry["name"]}")
        }
        onCreate(db)
        Log.d(TAG, "onUpgrade DataBase version from $oldVersion to $newVersion version!")
    }

    private fun open() {
        db = writableDatabase
    }

    private fun objToTable(
        tableName: String,
        columns: Map<String, Any>,
        primaryKeys: Array<String>,
        foreignKeys: Map<String, String> = emptyMap()
    ): String {
        val table = StringBuilder("CREATE TABLE IF NOT EXISTS $tableName")
        table
            .append(
                columns.filter { (_, value) ->
                    value !is List<*> && value !is Component
                }.map { (key, value) ->
                    when (value) {
                        is String -> "$key TEXT DEFAULT \"$value\""
                        is Int -> "$key INTEGER DEFAULT $value"
                        is Double -> "$key REAL DEFAULT $value"
                        is Date -> "$key INTEGER DEFAULT ${value.time}"
                        else -> throw java.lang.IllegalArgumentException("$value e' di un tipo non compatibile! --> ${value.javaClass.simpleName}")
                    }
                }.joinToString(separator = ",", postfix = ",")
            )
            .append(primaryKeys.joinToString(separator = ",", prefix = "PRIMARY KEY (", postfix = ","))
            .append(
                foreignKeys.map { (column, table) ->
                    "FOREIGN KEY ($column) REFERENCES $table (id)"
                }.joinToString(separator = ",", prefix = ",", postfix = ")"))

        return table.toString()
    }

    fun beginTransaction() {
        db.beginTransaction()
    }

    fun rollback() {
        db.endTransaction()
    }

    fun commit() {
        db.setTransactionSuccessful()
        db.endTransaction()
    }

    fun isEmpty(table: String): Boolean {
        return try {
            selectOne(table, arrayOf("COUNT(*) AS rows"))["rows"] as Int == 0
        } catch (e: SQLiteException) {
            // se entra qua probabilmente la tabella non esiste
            Log.w(TAG, "Database vuoto")
            close()
            true
        }
    }

    fun exec(query: String) {
        open()
        db.execSQL(query)
        close()
    }

    fun insert(table: String, values: ContentValues, conflict: Boolean = false): Boolean {
        open()
        val result =
            if (conflict) {
                db.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_IGNORE)
            } else {
                db.insert(table, null, values)
            }
        Log.d(TAG, "Righe presenti in $table: $result")
        close()
        return result.toInt() != -1
    }

    fun select(
        table: String, columns: Array<String> = emptyArray(),
        whereClause: String = "", whereValues: Array<String> = emptyArray(),
        groupBy: String = "", having: String = "", orderBy: String = "",
        limit: Int = -1
    ): List<Map<String, Any?>> {
        open()
        val cursor = db.query(
            table,
            if (columns.isNotEmpty()) columns else null,
            if (whereClause.isNotEmpty()) whereClause else null,
            if (whereValues.isNotEmpty()) whereValues else null,
            if (groupBy.isNotEmpty()) groupBy else null,
            if (having.isNotEmpty()) having else null,
            if (orderBy.isNotEmpty()) orderBy else null,
            if (limit > 0) "$limit" else null
        )
        val list = mutableListOf<Map<String, Any?>>()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(getRow(cursor))
                } while (cursor.moveToNext())
            }
            Log.d(TAG, "Righe selezionate in $table: ${list.size}")
        }
        cursor.close()
        close()
        return list
    }

    fun selectOne(
        table: String, columns: Array<String> = emptyArray(),
        whereClause: String = "1 = 1", whereValues: Array<String> = emptyArray()
    ): Map<String, Any?> {
        return select(table, columns, whereClause, whereValues, limit = 1).getOrElse(0) { mutableMapOf() }
    }

    // select by primary key or select by foreign key (entrambi saranno id)
    // la foreign key servira' per prendere tutte le schede di un atleta
    fun selectByKey(
        table: String,
        key: String,
        value: String,
        orderBy: String = "",
        limit: Int = -1
    ): List<Map<String, Any?>> {
        return select(table, whereClause = "$key = ?", whereValues = arrayOf(value), orderBy = orderBy, limit = limit)
    }

    fun selectOneByKey(table: String, key: String, value: String, orderBy: String = ""): Map<String, Any?> {
        return selectByKey(table, key, value, orderBy = orderBy, limit = 1).getOrElse(0) { mutableMapOf() }
    }

    private fun getRow(cursor: Cursor): Map<String, Any?> {
        val row = mutableMapOf<String, Any?>()
        cursor.columnNames.forEachIndexed { index, column ->
            val value: Any? = when (cursor.getType(index)) {
                Cursor.FIELD_TYPE_BLOB -> cursor.getBlob(index)
                Cursor.FIELD_TYPE_FLOAT -> cursor.getDouble(index)
                Cursor.FIELD_TYPE_INTEGER -> if (cursor.getLong(index) > Int.MAX_VALUE) cursor.getLong(index) else cursor.getInt(
                    index
                )
                Cursor.FIELD_TYPE_STRING -> cursor.getString(index)
                else -> null
            }
            row[column] = value
        }
        return row
    }

    fun update(table: String, values: ContentValues, whereClause: String, whereValues: Array<String>): Boolean {
        open()
        val result = db.update(table, values, whereClause, whereValues)
        Log.d(TAG, "Righe aggiornate in $table: $result")
        close()
        return result != -1
    }

    fun delete(table: String, whereClause: String, whereValues: Array<String>): Boolean {
        open()
        val result = db.delete(table, whereClause, whereValues)
        Log.d(TAG, "Righe cancellate in $table: $result")
        close()
        return result != -1
    }

}