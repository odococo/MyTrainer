package com.example.mytrainer.fragment.trainer


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.mytrainer.R
import com.example.mytrainer.adapter.trainer.ScheduleAdapter
import com.example.mytrainer.component.ScheduleRequest
import com.example.mytrainer.component.TrainingSchedule
import com.example.mytrainer.database.locale.Query
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment : Fragment() {
    private var request: ScheduleRequest = ScheduleRequest()
    private val schedule: TrainingSchedule = TrainingSchedule()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            val id = it.getString(REQUEST, "")
            if (id.isNotEmpty()) {
                request = Query.getInstance().getRequest(id)
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
        viewpager.adapter = ScheduleAdapter(context, 4)
        sliding_tabs.setupWithViewPager(viewpager)
    }

    companion object {
        const val REQUEST = "request"
    }

}
