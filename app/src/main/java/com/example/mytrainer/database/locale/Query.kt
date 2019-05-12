package com.example.mytrainer.database.locale

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.mytrainer.auth.Auth
import com.example.mytrainer.component.*
import com.example.mytrainer.fragment.login.LoadingFragment
import com.example.mytrainer.utils.SingletonHolder1
import com.example.mytrainer.database.remote.Query as Remote

class Query
private constructor(val context: Context) {
    private val TAG = "QuerySQLite"
    private val db: DataBaseOpenHelper = DataBaseOpenHelper.getInstance(context)

    companion object : SingletonHolder1<Query, Context>(::Query)

    fun init(loading: LoadingFragment) {
        loading.step(1)
        Remote.getAllExercises { exercises ->
            addAll(exercises)
            loading.step(2)
            Remote.getAllSchedules(getUser()) { schedules ->
                addAll(schedules)
                loading.step(3)
                Remote.getAllRequests(getUser()) { requests ->
                    addAll(requests)
                    loading.step(4)
                }
            }
        }
    }

    fun addAll(list: List<Component>) {
        list.forEach { el ->
            when (el) {
                is Exercise -> addExercise(el)
                is TrainingSchedule -> addTrainingSchedule(el)
                is User -> addUser(el)
                is ScheduleRequest -> addScheduleRequest(el)
                else -> IllegalArgumentException("Non c'e' una query per $el")
            }
        }
    }

    fun clearAndRestoreDB() {
        Log.e(TAG, "Pulendo il db e!")
        Auth.getInstance().logout()
        db.onUpgrade(db.writableDatabase, 0, SQLContract.DATABASE_VERSION)
    }

    fun addExercise(exercise: Exercise): Boolean {
        db.beginTransaction()
        val values = ContentValues()
        values.put(SQLContract.Exercises.ID, exercise.id)
        values.put(SQLContract.Exercises.DESCRIPTION, exercise.description)

        if (db.insert(SQLContract.Exercises.NAME, values, SQLiteDatabase.CONFLICT_IGNORE)) {
            if (addExerciseTypes(exercise)) {
                db.commit()
            }

            return true
        } else {
            Log.w(TAG, "Errore inserimento esercizio in e: $exercise")
        }
        db.rollback()

        return false
    }

    private fun addExerciseTypes(exercise: Exercise): Boolean {
        var result = 0
        for (type in exercise.types) {
            val values = ContentValues()
            values.put(SQLContract.ExerciseTypes.ID, exercise.id)
            values.put(SQLContract.ExerciseTypes.TYPE, type)
            if (!db.insert(SQLContract.ExerciseTypes.NAME, values)) {
                Log.w(TAG, "Errore inserimento tipi dell'esercizio in e: $exercise")
            } else {
                result++
            }
        }
        return result == exercise.types.size
    }

    fun getExercise(name: String): Exercise {
        val map = db.selectOneByKey(SQLContract.Exercises.NAME, SQLContract.Exercises.ID, name)

        return Exercise().fromMap(map)
    }

    fun addUser(user: User): Boolean {
        val values = ContentValues()
        values.put(SQLContract.Users.ID, user.id)
        values.put(SQLContract.Users.FIRSTNAME, user.firstName)
        values.put(SQLContract.Users.LASTNAME, user.lastName)
        values.put(SQLContract.Users.TYPE, user.type)

        return db.insert(SQLContract.Users.NAME, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun updateUser(user: User): Boolean {
        val values = ContentValues()
        values.put(SQLContract.Users.ID, user.id)
        values.put(SQLContract.Users.FIRSTNAME, user.firstName)
        values.put(SQLContract.Users.LASTNAME, user.lastName)
        values.put(SQLContract.Users.TYPE, user.type)

        return db.update(SQLContract.Users.NAME, values, "${SQLContract.Users.ID} = ?", arrayOf(user.id))
    }

    fun getUser(): User {
        return getUserById(Auth.getInstance().getId())
    }

    fun getUserById(id: String): User {
        val map = db.selectOneByKey(SQLContract.Users.NAME, SQLContract.Users.ID, id)

        return User().fromMap(map)
    }

    fun getAllUsers(): List<User> {
        return db.select(SQLContract.Users.NAME).map { user -> User().fromMap(user) }
    }

    fun getAllUsersType(type: String): List<User> {
        return db.select(
            SQLContract.Users.NAME,
            whereClause = "${SQLContract.Users.TYPE} = ?",
            whereValues = arrayOf(type)
        ).map { user -> User().fromMap(user) }
    }

    fun addTrainingSchedule(schedule: TrainingSchedule): Boolean {
        db.beginTransaction()
        addUser(schedule.athlete)
        addUser(schedule.trainer)

        val values = ContentValues()
        values.put(SQLContract.TrainingSchedules.ID, schedule.id)
        values.put(SQLContract.TrainingSchedules.TRAINER, schedule.trainer.id)
        values.put(SQLContract.TrainingSchedules.ATHLETE, schedule.athlete.id)
        values.put(SQLContract.TrainingSchedules.STARTDATE, schedule.startDate.time)
        if (db.insert(SQLContract.TrainingSchedules.NAME, values, SQLiteDatabase.CONFLICT_IGNORE)) {
            if (addTrainingExercises(schedule)) {
                db.commit()

                return true
            } else {
                Log.w(TAG, "Errore inserimento esercizi scheda: $schedule")
            }
        } else {
            Log.w(TAG, "Errore inserimento scheda: $schedule")
        }
        db.rollback()

        return false
    }

    fun addTrainingExercises(schedule: TrainingSchedule): Boolean {
        var result = 0
        for (exercise in schedule.exercises) {
            val values = ContentValues()
            values.put(SQLContract.TrainingExercises.EXERCISE, exercise.id)
            values.put(SQLContract.TrainingExercises.SCHEDULE, schedule.id)
            values.put(SQLContract.TrainingExercises.DAY, exercise.day)
            values.put(SQLContract.TrainingExercises.SERIES, exercise.series)
            values.put(SQLContract.TrainingExercises.REPS, exercise.reps)
            values.put(SQLContract.TrainingExercises.RECOVERYTIME, exercise.recoveryTime)
            if (!db.insert(SQLContract.TrainingExercises.NAME, values)) {
                Log.w(TAG, "Errore inserimento esercizio scheda: $exercise")
            } else {
                result++
            }
        }

        return result == schedule.exercises.size
    }


    fun getSchedule(id: String, day: Int = 0): TrainingSchedule {
        val map =
            db.selectOneByKey(SQLContract.TrainingSchedules.NAME, SQLContract.TrainingSchedules.ID, id) as MutableMap

        val schedule = TrainingSchedule().fromMap(map)

        val whereClause =
            "${SQLContract.TrainingExercises.SCHEDULE} = ? AND " + if (day == 0) "${SQLContract.TrainingExercises.DAY} <> ?" else "${SQLContract.TrainingExercises.DAY} = ?"
        val exercises =
            db.select(SQLContract.TrainingExercises.NAME, whereClause = whereClause, whereValues = arrayOf(id, "$day"))
        schedule.exercises = exercises.map { ex -> TrainingExercise().fromMap(ex) }

        return schedule
    }

    fun getCurrentSchedule(): TrainingSchedule {
        val map = db.selectOneByKey(
            SQLContract.TrainingSchedules.NAME,
            SQLContract.TrainingSchedules.ATHLETE,
            Auth.getInstance().getId(),
            "${SQLContract.TrainingSchedules.STARTDATE} DESC"
        )

        return getSchedule(map.getOrDefault(SQLContract.TrainingSchedules.ID, "") as String)
    }

    fun getSchedules(): List<TrainingSchedule> {
        val schedules = db.selectByKey(
            SQLContract.TrainingSchedules.NAME,
            SQLContract.TrainingSchedules.ATHLETE,
            Auth.getInstance().getId(),
            "${SQLContract.TrainingSchedules.STARTDATE} DESC"
        )

        return schedules.map { schedule ->
            getSchedule(
                schedule.getOrDefault(
                    SQLContract.TrainingSchedules.ID,
                    ""
                ) as String
            )
        }
    }

    fun addScheduleRequest(request: ScheduleRequest): Boolean {
        val values = ContentValues()
        values.put(SQLContract.ScheduleRequests.ID, request.id)
        values.put(SQLContract.ScheduleRequests.FROM, request.athlete.id)
        values.put(SQLContract.ScheduleRequests.TO, request.trainer.id)
        values.put(SQLContract.ScheduleRequests.INFO, request.info)

        return db.insert(SQLContract.ScheduleRequests.NAME, values, SQLiteDatabase.CONFLICT_IGNORE)
    }

    fun getRequests(): List<ScheduleRequest> {
        /*val requests = db.selectByKey(
            SQLContract.ScheduleRequests.NAME,
            SQLContract.ScheduleRequests.TO,
            Auth.getInstance().getId()
        )*/
        val requests = db.select(SQLContract.ScheduleRequests.NAME)

        return requests.map { request -> ScheduleRequest().fromMap(request) }
    }

}