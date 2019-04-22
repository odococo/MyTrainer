package com.example.mytrainer.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mytrainer.R

class RequestScheduleAdapter: RecyclerView.Adapter<RequestScheduleAdapter.Companion.RequestScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestScheduleViewHolder {
        return RequestScheduleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_request_schedule, parent, false))
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: RequestScheduleViewHolder, position: Int) {
        holder.parameter1.text = "Some Parameter1 for request"
        holder.parameter2.text = "Some Parameter2 for request"
    }


    companion object {

        class RequestScheduleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            val parameter1: TextView = itemView.findViewById(R.id.requestParameter1)
            val parameter2: TextView = itemView.findViewById(R.id.requestParameter2)
        }
    }
}