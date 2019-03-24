package com.example.mytrainer.database.locale

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.mytrainer.database.locale.SQLContract.Companion.EXERCISE_RECOVERYTIME
import com.example.mytrainer.database.locale.SQLContract.Companion.EXERCISE_REPS
import com.example.mytrainer.database.locale.SQLContract.Companion.EXERCISE_SERIES
import com.example.mytrainer.database.locale.SQLContract.Companion.ID
import com.example.mytrainer.database.locale.SQLContract.Companion.TRAINING_EXERCISES_TABLE
import com.example.mytrainer.database.locale.SQLContract.Companion.USERS_TABLE
import com.example.mytrainer.database.locale.SQLContract.Companion.USER_BIRTHDATE
import com.example.mytrainer.database.locale.SQLContract.Companion.USER_EMAIL
import com.example.mytrainer.database.locale.SQLContract.Companion.USER_FIRSTNAME
import com.example.mytrainer.database.locale.SQLContract.Companion.USER_LASTNAME
import com.example.mytrainer.database.locale.SQLContract.Companion.USER_PSWD

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
        //birth_date è un INTEGER ma cmq conterrà un long che pio verrà trasformato in tipo date/localDate o quello che è
        val userTable: String = "CREATE TABLE $USERS_TABLE (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                "$USER_EMAIL TEXT, " +
                "$USER_PSWD TEXT" +
                "$USER_FIRSTNAME TEXT, " +
                "$USER_LASTNAME TEXT, " +
                "$USER_BIRTHDATE INTEGER);"

        val trainingExercise: String = "CREATE TABLE $TRAINING_EXERCISES_TABLE (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$EXERCISE_SERIES INTEGER, " +
                "$EXERCISE_REPS INTEGER, " +
                "$EXERCISE_RECOVERYTIME INTEGER);"

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