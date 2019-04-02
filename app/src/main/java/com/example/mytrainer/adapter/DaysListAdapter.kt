package com.example.mytrainer.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mytrainer.R
import com.example.mytrainer.component.Exercise

class DaysListAdapter: RecyclerView.Adapter<DaysListAdapter.Companion.RemindViewHolder> {

    private var data: List<Exercise>? = null

    constructor(data: List<Exercise>){
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysListAdapter.Companion.RemindViewHolder {
        return Companion.RemindViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false))
    }

    override fun getItemCount(): Int {
        return this.data!!.size
    }

    //Mette nel card view il titolo e la descrizione dell'esercizio
    override fun onBindViewHolder(holder: DaysListAdapter.Companion.RemindViewHolder, position: Int) {
        holder.title!!.text = data!![position].name
        holder.description!!.text = data!![position].description
    }

    companion object {

        class RemindViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            var cardView: CardView? = null
            var title: TextView? = null
            var description: TextView? = null

            init {
                cardView = itemView.findViewById(R.id.cardView)
                title = itemView.findViewById(R.id.exercise_title)
                description = itemView.findViewById(R.id.exercise_description)
            }

        }
    }
}