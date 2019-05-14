package com.example.mytrainer.component

import android.util.Log
import com.example.mytrainer.database.locale.Query
import com.example.mytrainer.database.locale.SQLContract
import com.example.mytrainer.database.remote.FirebaseContract

class ScheduleRequest(
    var athlete: User,
    var trainer: User,
    var info: String
) : Component() {

    constructor() : this(User(), User(), "")

    override fun withId(id: String): ScheduleRequest {
        this.id = id

        return this
    }

    override fun toMap(): Map<String, Any> = mutableMapOf(
        FirebaseContract.ScheduleRequests.FROM to athlete,
        FirebaseContract.ScheduleRequests.TO to trainer,
        FirebaseContract.ScheduleRequests.INFO to info
    )

    override fun fromMap(map: Map<String, Any?>): ScheduleRequest {
        val request = ScheduleRequest()
        map.forEach { (key, value) ->
            when (key) {
                SQLContract.ScheduleRequests.ID -> request.id = value as String
                SQLContract.ScheduleRequests.FROM -> request.athlete = Query.getInstance().getUserById(value as String)
                SQLContract.ScheduleRequests.TO -> request.trainer = Query.getInstance().getUserById(value as String)
                SQLContract.ScheduleRequests.INFO -> request.info = value as String
                else -> Log.w("ScheduleRequest", "$key: $value non appartiene a ${this.javaClass.simpleName}!")
            }
        }
        return request
    }
}