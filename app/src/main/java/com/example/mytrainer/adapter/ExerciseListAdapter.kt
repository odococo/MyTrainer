package com.example.mytrainer.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mytrainer.R
import com.example.mytrainer.component.TrainingExercise

class ExerciseListAdapter: RecyclerView.Adapter<ExerciseListAdapter.Companion.ExerciseViewHolder>() {

    // Contiene tutti gli esercizi relativi ad un solo giorno.
    //TODO Questa classe è da rivedere.
    //TODO Bisogna capire se è possibile visualizzare, per ogni giorni, esercizi diversi. Per ora gli esercizi sono tutti uguali, quelli inizializzati nel costruttore
    private val data: List<TrainingExercise>

    init {
        this.data = ArrayList<TrainingExercise>()
        data.add(TrainingExercise(0, 3, 15, 90))
        data.add(TrainingExercise(0, 4, 10, 120))
        data.add(TrainingExercise(0, 5, 8, 180))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val title = "Esercizio ..."
        val series = "Series "
        val per = " x "
        val reps = " Reps"
        val recoveryTime = "Recovery time - "
        val timeUnit = " sec"

        holder.title.text = title
        //holder.title!!.text = data!![position].id //Probabilmente c'è un bug con il nome degli esercizi. Da rivedere.
        holder.overallWork.text = series + data[position].series + per + data[position].reps + reps
        holder.recoveryTime.text = recoveryTime + data[position].recoveryTime + timeUnit

        //holder.recoveryTime!!.text = data!![position].description
    }

    companion object {

        class ExerciseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            var cardView: CardView = itemView.findViewById(R.id.cardView)
            var title: TextView = itemView.findViewById(R.id.exercise_title)
            var overallWork: TextView = itemView.findViewById(R.id.overall_work)//Questa variabile viene composta dalla quantita di serie per la quantità di ripetizioni che è il lavoro complessivo.
            var recoveryTime: TextView = itemView.findViewById(R.id.recovery_time)
           //var description: TextView? = null //Per ora è la variabile opzionale, molto probabile che la utilizzeremo nella variante estesa dell'applicazione.
        }
    }
}