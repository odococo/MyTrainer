package com.example.mytrainer.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mytrainer.R
import com.example.mytrainer.component.TrainingExercise

class TrainingDayListAdapter: RecyclerView.Adapter<TrainingDayListAdapter.Companion.ExerciseViewHolder> {

    private var data: List<TrainingExercise> // Contiene tutti gli esercizi relativi ad un solo giorno. Viene passatto nel costruttore.

    constructor(data: List<TrainingExercise>){
        this.data = data
    }

    //Qui viene passato il layout dell'esrcizio che poi verrà messo a schermo
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingDayListAdapter.Companion.ExerciseViewHolder {
        return Companion.ExerciseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false))
    }

    override fun getItemCount(): Int {
        return this.data.size
    }

    //Il metodo che mette a schermo le info relative ad ogni esercizio.
    //La quantità deli esercizi può essere variabile.
    override fun onBindViewHolder(holder: TrainingDayListAdapter.Companion.ExerciseViewHolder, position: Int) {
        val title: String = "Esercizio ..."
        val series: String = "Series "
        val per: String = " x "
        val reps: String = " Reps"
        val recoveryTime: String = "Recovery time - "
        val timeUnit: String = " sec"

        holder.title.text = title
        //holder.title!!.text = data!![position].id //Probabilmente c'è un bug con il nome degli esercizi. Da rivedere.
        holder.overallWork.text = series + data!![position].series + per + data!![position].reps + reps
        holder.recoveryTime.text = recoveryTime + data!![position].recoveryTime + timeUnit

        //holder.recoveryTime!!.text = data!![position].description
    }

    companion object {

        class ExerciseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            //Elenco dei campi relativi ad ogni esercizio
            var cardView: CardView = itemView.findViewById(R.id.cardView) //è la struttura grafica in cui vengono messi gli esercizi
            var title: TextView = itemView.findViewById(R.id.exercise_title)
            var overallWork: TextView = itemView.findViewById(R.id.overall_work)//Questa variabile viene composta dalla quantita di serie per la quantità di ripetizioni che è il lavoro complessivo.
            var recoveryTime: TextView = itemView.findViewById(R.id.recovery_time)
           //var description: TextView? = null //Per ora è la variabile opzionale, molto probabile che la utilizzeremo nella variante estesa dell'applicazione.
        }
    }
}