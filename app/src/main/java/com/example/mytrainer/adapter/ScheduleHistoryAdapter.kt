package com.example.mytrainer.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mytrainer.MainActivity
import com.example.mytrainer.R
import com.example.mytrainer.database.locale.Query

class ScheduleHistoryAdapter(val context: Context) :
    RecyclerView.Adapter<ScheduleHistoryAdapter.Companion.ScheduleHistoryViewHolder>() {

    private val schedules = Query.getInstance().getSchedules()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleHistoryViewHolder {
        return ScheduleHistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_schedule_history, parent, false))
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    override fun onBindViewHolder(holder: ScheduleHistoryViewHolder, position: Int) {
        holder.scheduleName.text = "Scheda "
        holder.trainer.text = schedules[position].trainer.name()
        holder.startDate.text = schedules[position].startDate.toString()
        holder.view.setOnClickListener {
            (context as MainActivity).changeSchedule(schedules[position])
        }
    }

    companion object {

        class ScheduleHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val view = itemView
            val scheduleName: TextView = itemView.findViewById(R.id.scheduleName)
            val trainer: TextView = itemView.findViewById(R.id.trainer)
            val startDate: TextView = itemView.findViewById(R.id.startDate)

        }
    }
}