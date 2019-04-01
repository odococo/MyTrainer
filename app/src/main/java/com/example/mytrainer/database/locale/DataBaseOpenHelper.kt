package com.example.mytrainer.database.locale

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.mytrainer.component.Component
import com.example.mytrainer.database.locale.SQLContract.Companion.BIRTH_DATE
import com.example.mytrainer.database.locale.SQLContract.Companion.EMAIL
import com.example.mytrainer.database.locale.SQLContract.Companion.FIRST_NAME
import com.example.mytrainer.database.locale.SQLContract.Companion.ID
import com.example.mytrainer.database.locale.SQLContract.Companion.LAST_NAME
import com.example.mytrainer.database.locale.SQLContract.Companion.PASSWORD
import com.example.mytrainer.database.locale.SQLContract.Companion.RECOVERY_TIME
import com.example.mytrainer.database.locale.SQLContract.Companion.REPS
import com.example.mytrainer.database.locale.SQLContract.Companion.SERIES
import com.example.mytrainer.database.locale.SQLContract.Companion.TRAINING_EXERCISES
import com.example.mytrainer.database.locale.SQLContract.Companion.USERS

//Singleton
class DataBaseOpenHelper: SQLiteOpenHelper {

    private val TAG: String = "DataBaseOpenHelper"

    companion object {

        private const val DATABASE_NAME: String = "SQLiteDB"
        private const val DATABASE_VERSION: Int = 1

        private var dbHelper: DataBaseOpenHelper? = null //Se non inizializzo a null, errore in esecuzione.
        @Synchronized fun getInstance(context: Context): DataBaseOpenHelper{
            if (dbHelper == null)
                dbHelper = DataBaseOpenHelper(context)
            return dbHelper as DataBaseOpenHelper //Il cast esplicito è necessario, altrimenti errore in compilazione.
        }
    }

    private constructor(context: Context): super(context, DATABASE_NAME, null, DATABASE_VERSION)

    fun objToTable(obj: Component): String {
        val map = obj.toMap()
        val multiple = mutableListOf<String>()
        val table: StringBuilder = StringBuilder("CREATE TABLE ${obj.javaClass.simpleName} ( id TEXT PRIMARY KEY ")
        val columns: List<String> = map.map { entry ->
            return when (entry.value) {
                is String -> "${entry.key} TEXT DEFAULT ${entry.value}"
                is Int -> "${entry.key} INTEGER DEFAULT ${entry.value}"
                is Double -> "${entry.key} REAL DEFAULT ${entry.value}"
                is List<*> -> {
                    multiple.add(objToTable((entry.value as List<*>).get(0) as Component))
                    ""
                }
                is Component -> ""
                else -> throw IllegalArgumentException("$obj ha un tipo (${entry.value.javaClass} non valido!")
            }
        }
        columns.forEach { column ->
            if (!column.isEmpty()) {
                table.append(column)
            }
        }
        return table.toString()

    }

    override fun onCreate(db: SQLiteDatabase?) {
        //birth_date è un INTEGER ma cmq conterrà un long che pio verrà trasformato in tipo date/localDate o quello che è
        val userTable: String = "CREATE TABLE $USERS (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                "$EMAIL TEXT, " +
                "$PASSWORD TEXT" +
                "$FIRST_NAME TEXT, " +
                "$LAST_NAME TEXT, " +
                "$BIRTH_DATE INTEGER);"

        val trainingExercise: String = "CREATE TABLE $TRAINING_EXERCISES (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$SERIES INTEGER, " +
                "$REPS INTEGER, " +
                "$RECOVERY_TIME INTEGER);"
        /*
        QUESTA PARTE NON è FINITA. lA PROF DI ANALISI ARRIVA TRA UN Pò E NON HO TEMPO FINIRE ADESSO :=)
         */
        val query: String = userTable+trainingExercise
        db?.execSQL(query)
        Log.d(TAG, "onCreate new DataBase")
    }

    //Per aggiornare db, prima bisogna cancellarlo e poi richiamare onCreate sopra passando il db aggiornato(specificando la vecchia e nuova versione).
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query: String = "DROP TABLE IF EXISTS user" //TODO questo è solo un esempio.
        db?.execSQL(query)
        this.onCreate(db)
        Log.d(TAG, "onUpgrade DataBase version from $oldVersion to  $newVersion version!")
    }

}