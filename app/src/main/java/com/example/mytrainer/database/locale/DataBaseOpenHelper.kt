package com.example.mytrainer.database.locale

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

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

    override fun onCreate(db: SQLiteDatabase?) {
        //date_of_birth è un INTEGER ma cmq conterrà un long che pio verrà trasformato in tipo date/localDate o quello che è
        val userTable: String = "CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, first_name TEXT, last_name TEXT, date_of_birth INTEGER);"
        val trainingExercise: String = "CREATE TABLE trainingExercise(id INTEGER PRIMARY KEY AUTOINCREMENT, series INTEGER, reps INTEGER, recoveryTime INTEGER);"
        //Queste sono due tabelle principali. Dobbiamo crare un modello relazionale adeguato, pensato bene e poi implementarlo.

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