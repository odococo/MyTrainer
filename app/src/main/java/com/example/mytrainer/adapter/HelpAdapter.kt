package com.example.mytrainer.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mytrainer.R

class HelpAdapter: RecyclerView.Adapter<HelpAdapter.Companion.HelpViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpViewHolder {
        return HelpViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_help, parent, false))
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: HelpViewHolder, position: Int) {
        holder.helpText.text = "Some Help Text"
    }

    companion object {

        class HelpViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            val helpText: TextView = itemView.findViewById(R.id.help)
        }
    }
}