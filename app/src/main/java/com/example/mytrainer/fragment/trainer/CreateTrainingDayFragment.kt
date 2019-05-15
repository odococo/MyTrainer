package com.example.mytrainer.fragment.trainer


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytrainer.R
import com.example.mytrainer.adapter.trainer.ExercisesAdapter
import com.example.mytrainer.component.TrainingExercise
import kotlinx.android.synthetic.main.fragment_create_training_day.*
import kotlinx.android.synthetic.main.fragment_users.list
import com.example.mytrainer.database.locale.Query as locale
import com.example.mytrainer.database.remote.Query as remote

class CreateTrainingDayFragment : Fragment() {
    private var day: Int = -1
    var exercises = mutableListOf<TrainingExercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            day = it.getInt(DAY, -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_training_day, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = ExercisesAdapter(exercises, context)

        new_exercise.setOnClickListener {
            // da aggiungere un popup o un nuovo fragment per scegliere i vari campi
            exercises.add(TrainingExercise(day, 1, 1, 1).withId("Prova1"))
            println("Esercizi: ${exercises.size}")
            list.adapter = ExercisesAdapter(exercises, context)
        }
    }

    companion object {
        const val DAY = "day"
    }

}