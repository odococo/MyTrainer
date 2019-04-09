package com.example.mytrainer.database.locale

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.mytrainer.auth.Auth
import com.example.mytrainer.component.*
import com.example.mytrainer.database.SQLContract
import com.example.mytrainer.database.remote.Firestore
import com.example.mytrainer.utils.SingletonHolder1

//Singleton
class DataBaseOpenHelper
private constructor(private val context: Context) :
    SQLiteOpenHelper(context, SQLContract.DATABASE_NAME, null, SQLContract.DATABASE_VERSION) {

    private val TAG: String = "DataBaseOpenHelper"
    private var db: SQLiteDatabase

    companion object : SingletonHolder1<DataBaseOpenHelper, Context>(::DataBaseOpenHelper)

    init {
        Log.d(TAG, "Init DataBaseOpenHelper")
        db = writableDatabase
        val clear = isEmpty("users")
        if (clear) {
            onUpgrade(db, 0, SQLContract.DATABASE_VERSION) // non lo chiama in automatico
        } else {
            onCreate(db)
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        // per alcune tabelle vengono create insieme piu' tabelle ma execSQL ne esegue una sola alla volta, fino a ;
        val tables = mutableListOf<String>()
        tables.add(objToTable(User()))
        tables.add(objToTable(Exercise()))
        tables.add(objToTable(TrainingSchedule()))
        tables.add(objToTable(TrainingExercise(), fkeys = listOf(TrainingSchedule())))
        tables
            .forEach { table ->
                table.split(";").forEach { query ->
                    if (!query.isEmpty()) { // potrebbe essere stringa vuota, non so come pero'
                        //exec(query)
                        println(query)
                    }
                }
            }
        Log.d(TAG, "onCreate new DataBase")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        select("sqlite_master", arrayOf("name"), "name NOT LIKE ?", arrayOf("sqlite%"), -1).forEach { entry ->
            exec("DROP TABLE ${entry["name"]}")
        }
        onCreate(db)
        initTables()
        Log.d(TAG, "onUpgrade DataBase version from $oldVersion to $newVersion version!")
    }

    fun open() {
        db = writableDatabase
    }

    // TODO list di Component -> nuova tabella
    private fun objToTable(obj: Component, name: String = "", fkeys: List<Component> = emptyList()): String {
        val tablename = if (name.isEmpty()) SQLContract.getTableName(obj) else name
        val map = obj.toMap()
        val table = "CREATE TABLE IF NOT EXISTS $tablename"
        val multipleAttributes = mutableListOf<String>()
        val columns = mutableListOf("id TEXT PRIMARY KEY")
        val foreignKeys = mutableListOf<String>()
        columns.addAll(
            map.filter { entry ->
                entry.value !is List<*>
            }.map { entry ->
                when (entry.value) {
                    is String -> "${entry.key} TEXT DEFAULT \"${entry.value}\""
                    is Int -> "${entry.key} INTEGER DEFAULT ${entry.value}"
                    is Double -> "${entry.key} REAL DEFAULT ${entry.value}"
                    is Component -> {
                        foreignKeys.add(
                            "FOREIGN KEY (${entry.key}_id) REFERENCES ${entry.value.javaClass.simpleName} (id)"
                        )
                        "${entry.key}_id TEXT"
                    }
                    else -> throw IllegalArgumentException("$obj ha un tipo (${entry.value.javaClass} non valido!")
                }
            })
        map.filter { entry ->
            entry.value is List<*> && !(entry.value as List<*>).isEmpty() && (entry.value as List<*>)[0] !is Component
        }.forEach { entry ->
            val element = (entry.value as List<*>)[0]
            val columns2 = mutableListOf("id TEXT PRIMARY KEY")
            val table2 = "CREATE TABLE IF NOT EXISTS ${tablename}_${entry.key}"
            columns2.add(
                when (element) {
                    is String -> "${entry.key} TEXT DEFAULT \"$element\""
                    is Int -> "${entry.key} INTEGER DEFAULT $element"
                    is Double -> "${entry.key} REAL DEFAULT $element"
                    else -> throw IllegalArgumentException("$obj ha un tipo (${entry.value.javaClass} non valido!")
                }
            )
            columns2.add("FOREIGN KEY (id) REFERENCES $tablename (id)")
            multipleAttributes.add(table2 + columns2.joinToString(prefix = "(", postfix = ")", separator = ","))
        }
        // per ereditarieta', mappata con un'altra tabella
        val superclass = obj.javaClass.superclass?.simpleName
        if (!superclass.equals("Component")) {
            foreignKeys.add(
                "FOREIGN KEY (id) REFERENCES $superclass (id)"
            )
        }
        // per le associazioni 1 a n vengono passate come parametro
        for (fkey in fkeys) {
            val referenceTable = fkey.javaClass.simpleName
            columns.add("${referenceTable}_id TEXT")
            foreignKeys.add(
                "FOREIGN KEY (${referenceTable}_id) REFERENCES $referenceTable (id)"
            )
        }
        columns.addAll(foreignKeys)
        return table +
                columns.joinToString(prefix = "(", postfix = ")", separator = ",") +
                multipleAttributes.joinToString(prefix = ";", postfix = "", separator = "")
    }

    private fun initTables() {
        // prendo tutti gli esercizi
        Firestore.getAll<Exercise>(SQLContract.getTableName(Exercise())) { exercises ->
            exercises.forEach { exercise ->
                Query.getInstance(context).addExercise(exercise)
            }
            Log.d(TAG, "Aggiunti ${exercises.size} esercizi!")
        }
        // prendo l'utente corrente
        Firestore.get<User>(SQLContract.getTableName(User()), Auth.getId()) { user ->
            Query.getInstance(context).addUser(user)
            Log.d(TAG, "Aggiunto utente $user")
        }
    }

    private fun isEmpty(table: String): Boolean {
        return selectOne(table, arrayOf("COUNT(*) AS rows"))["rows"] as Int == 0
    }

    fun exec(query: String) {
        open()
        db.execSQL(query)
        close()
    }

    fun insert(table: String, values: ContentValues): Boolean {
        open()
        val result = db.insert(table, null, values)
        Log.d(TAG, "Righe presenti in $table: $result")
        close()
        return result.toInt() != -1
    }

    fun select(
        table: String, columns: Array<String> = emptyArray(),
        whereClause: String = "1 = 1", whereValues: Array<String> = emptyArray(),
        limit: Int = -1
    ): List<Map<String, Any?>> {
        open()
        val cursor = db.query(
            table, columns, whereClause, whereValues,
            null, null, null, if (limit > 0) "$limit" else null
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
        return select(table, columns, whereClause, whereValues, limit = 1).getOrElse(0) { _ -> mapOf() }
    }

    // select by primary key or select by foreign key (entrambi saranno id)
    // la foreign key servira' per prendere tutte le schede di un atleta
    fun selectByKey(table: String, key: String, value: String, limit: Int = -1): List<Map<String, Any?>> {
        return select(table, whereClause = "$key = ?", whereValues = arrayOf(value), limit = limit)
    }

    fun selectOneByKey(table: String, key: String, value: String): Map<String, Any?> {
        return selectByKey(table, key, value, 1).getOrElse(0) { _ -> mapOf() }
    }

    private fun getRow(cursor: Cursor): Map<String, Any?> {
        val row = mutableMapOf<String, Any?>()
        cursor.columnNames.forEachIndexed { index, column ->
            val value: Any? = when (cursor.getType(index)) {
                Cursor.FIELD_TYPE_BLOB -> cursor.getBlob(index)
                Cursor.FIELD_TYPE_FLOAT -> cursor.getDouble(index)
                Cursor.FIELD_TYPE_INTEGER -> cursor.getInt(index)
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

/*

companion object {

    private const val DATABASE_NAME: String = "SQLiteDB"
    private const val DATABASE_VERSION: Int = 1
    val instance = DataBaseOpenHelper()

    private var dbHelper: DataBaseOpenHelper? = null //Se non inizializzo a null, errore in esecuzione.
    @Synchronized fun getInstance(context: Context): DataBaseOpenHelper{
        if (dbHelper == null)
            dbHelper = DataBaseOpenHelper(context)
        return dbHelper as DataBaseOpenHelper //Il cast esplicito è necessario, altrimenti errore in compilazione.
    }
}

private constructor(context: Context): super(context, DATABASE_NAME, null, DATABASE_VERSION)

override fun onCreate(db: SQLiteDatabase) {
    //birth_date è un INTEGER ma cmq conterrà un long che pio verrà trasformato in tipo date/localDate o quello che è
    val users = objToTable(User(), tables[0])
    val exercises = objToTable(Exercise(), tables[1])
    val schedule = objToTable(TrainingSchedule(), tables[2])
    val training_exercise = objToTable(TrainingExercise(), tables[3])
    val query: String = users + exercises + schedule + training_exercise
    db.execSQL(query)
    Log.d(TAG, "onCreate new DataBase")
}

//Per aggiornare db, prima bisogna cancellarlo e poi richiamare onCreate sopra passando il db aggiornato(specificando la vecchia e nuova versione).
override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    val query: String = "DROP TABLE IF EXISTS user" //TODO questo è solo un esempio.
    db?.execSQL(query)
    this.onCreate(db)
    Log.d(TAG, "onUpgrade DataBase version from $oldVersion to  $newVersion version!")
}*/