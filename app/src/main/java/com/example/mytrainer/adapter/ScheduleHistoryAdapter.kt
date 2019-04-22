package com.example.mytrainer.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mytrainer.R
import com.example.mytrainer.component.TrainingSchedule

class ScheduleHistoryAdapter: RecyclerView.Adapter<ScheduleHistoryAdapter.Companion.ScheduleHistoryViewHolder> {

    private val schedules: List<TrainingSchedule>

    constructor(){
        this.schedules = ArrayList<TrainingSchedule>()
        schedules.add(TrainingSchedule())
        schedules.add(TrainingSchedule())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleHistoryViewHolder {
        return ScheduleHistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_schedule_history, parent, false))
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    override fun onBindViewHolder(holder: ScheduleHistoryViewHolder, position: Int) {
        holder.scheduleName.text = "Scheda "
        holder.scheduleDescription.text = "Descrizione scheda "
    }

    companion object {

        class ScheduleHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            val cardView: CardView = itemView.findViewById(R.id.scheduleHistoryCardView)
            val scheduleName: TextView = itemView.findViewById(R.id.scheduleName)
            val scheduleDescription: TextView = itemView.findViewById(R.id.scheduleDescription)

        }
    }
}