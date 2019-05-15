package com.example.mytrainer.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mytrainer.R
import com.example.mytrainer.component.TrainingSchedule
import com.example.mytrainer.database.locale.Query

class ScheduleHistoryAdapter: RecyclerView.Adapter<ScheduleHistoryAdapter.Companion.ScheduleHistoryViewHolder>() {

    private val schedules = Query.getInstance().getSchedules()

    init {
        println(schedules)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleHistoryViewHolder {
        return ScheduleHistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_schedule_history, parent, false))
    }

    override fun getItemCount(): Int {
        return schedules.size
    }



    override fun onBindViewHolder(holder: ScheduleHistoryViewHolder, position: Int) {
        holder.scheduleName.text = "Scheda "
        holder.trainer.text = schedules[position].trainer.firstName
        holder.startDate.text = schedules[position].startDate.toString()
    }

    companion object {

        class ScheduleHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            val scheduleName: TextView = itemView.findViewById(R.id.scheduleName)
            val trainer: TextView = itemView.findViewById(R.id.trainer)
            val startDate: TextView = itemView.findViewById(R.id.startDate)

        }
    }
}