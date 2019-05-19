package com.example.mytrainer.fragment.trainer


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mytrainer.R
import com.example.mytrainer.adapter.trainer.ScheduleAdapter
import com.example.mytrainer.component.ScheduleRequest
import com.example.mytrainer.component.TrainingSchedule
import kotlinx.android.synthetic.main.fragment_schedule.*
import java.util.*
import com.example.mytrainer.database.locale.Query as locale
import com.example.mytrainer.database.remote.Query as remote

class ScheduleFragment : Fragment() {
    private var request: ScheduleRequest = ScheduleRequest()
    private var days = 0
    private val schedule: TrainingSchedule = TrainingSchedule()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            val id = it.getString(REQUEST, "")
            if (id.isNotEmpty()) {
                request = locale.getInstance().getRequest(id)
                days = it.getInt(DAYS, 0)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewpager.adapter = ScheduleAdapter(context, days, request)
        sliding_tabs.setupWithViewPager(viewpager)
    }

    fun complete() {
        schedule.trainer = request.trainer
        schedule.athlete = request.athlete
        schedule.startDate = Date()
        schedule.exercises = (viewpager.adapter as ScheduleAdapter).getExercises()

        if (schedule.exercises.isNotEmpty()) {
            remote.addTrainingSchedule(schedule) {
                locale.getInstance().addTrainingSchedule(schedule)
                remote.deleteRequest(request) {
                    locale.getInstance().deleteRequest(request)
                }
            }
        } else {
            Toast.makeText(context, "Non tutti i giorni hanno degli esercizi!", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val REQUEST = "request"
        const val DAYS = "days"
    }

}
