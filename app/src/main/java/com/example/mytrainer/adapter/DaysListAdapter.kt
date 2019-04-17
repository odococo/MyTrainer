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

    private var data: List<Exercise>? = null // Contiene tutti gli esercizi relativi ad un solo giorno.
    private var title: String = "Esercizio ..."
    private val series: String = "Series 3 x 12 Reps"
    private val recoveryTime: String = "Recovery time - 1:30 min"

    constructor(data: List<Exercise>){
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysListAdapter.Companion.RemindViewHolder {
        return Companion.RemindViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false))
    }

    override fun getItemCount(): Int {
        return this.data!!.size
    }

    //Il metodo che mette a schermo le info relative ad ogni esercizio.
    override fun onBindViewHolder(holder: DaysListAdapter.Companion.RemindViewHolder, position: Int) {
        holder.title!!.text = title
        //holder.title!!.text = data!![position].id

        holder.series!!.text = series
        //holder.series!!.text = data!![position]

        holder.recoveryTime!!.text = recoveryTime
        //holder.recoveryTime!!.text = data!![position].description

       // holder.description!!.text = data!![position].description
    }

    companion object {

        class RemindViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            //Elenco dei campi relativi ad ogni esercizio
            var cardView: CardView? = null
            var title: TextView? = null
            var series: TextView? = null
            var recoveryTime: TextView? = null
            var description: TextView? = null

            init {
                cardView = itemView.findViewById(R.id.cardView)
                title = itemView.findViewById(R.id.exercise_title)
                series = itemView.findViewById(R.id.exercise_series)
                recoveryTime = itemView.findViewById(R.id.recovery_time)
               // description = itemView.findViewById(R.id.description)
            }

        }
    }
}