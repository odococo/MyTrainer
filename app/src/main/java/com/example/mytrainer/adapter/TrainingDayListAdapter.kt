package com.example.mytrainer.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mytrainer.R
import com.example.mytrainer.component.Exercise

class TrainingDayListAdapter: RecyclerView.Adapter<TrainingDayListAdapter.Companion.ExerciseViewHolder> {

    private var data: List<Exercise>? = null // Contiene tutti gli esercizi relativi ad un solo giorno. Viene passatto nel costruttore.

    constructor(data: List<Exercise>){
        this.data = data
    }

    //Qui viene passato il layout dell'esrcizio che poi verrà messo a schermo
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingDayListAdapter.Companion.ExerciseViewHolder {
        return Companion.ExerciseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false))
    }

    override fun getItemCount(): Int {
        return this.data!!.size
    }

    //Il metodo che mette a schermo le info relative ad ogni esercizio.
    override fun onBindViewHolder(holder: TrainingDayListAdapter.Companion.ExerciseViewHolder, position: Int) {
        var title: String = "Esercizio ..."
        val series: String = "Series 3"
        val per: String = " x "
        val reps: String = "12 Reps"
        val recoveryTime: String = "Recovery time - 1:30 min"

        holder.title!!.text = title
        //holder.title!!.text = data!![position].id

        holder.overall_work!!.text = series + per + reps
        //holder.overall_work!!.text = data!![position]

        holder.recoveryTime!!.text = recoveryTime

        //holder.recoveryTime!!.text = data!![position].description

    }

    companion object {

        class ExerciseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            //Elenco dei campi relativi ad ogni esercizio
            var cardView: CardView? = null
            var title: TextView? = null
            var overall_work: TextView? = null //Questa variabile viene composta dalla quantita di serie per la quantità di ripetizioni che è il lavoro complessivo.
            var recoveryTime: TextView? = null
           //var description: TextView? = null //Per ora è la variabile opzionale

            init {
                cardView = itemView.findViewById(R.id.cardView)
                title = itemView.findViewById(R.id.exercise_title)
                overall_work = itemView.findViewById(R.id.overall_work)
                recoveryTime = itemView.findViewById(R.id.recovery_time)
               //description = itemView.findViewById(R.id.description)
            }

        }
    }
}