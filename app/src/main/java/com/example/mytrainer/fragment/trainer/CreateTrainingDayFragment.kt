package com.example.mytrainer.fragment.trainer


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.mytrainer.R
import com.example.mytrainer.adapter.trainer.ExercisesAdapter
import com.example.mytrainer.component.TrainingExercise
import kotlinx.android.synthetic.main.fragment_create_training_day.*
import kotlinx.android.synthetic.main.fragment_users.list
import com.example.mytrainer.database.locale.Query as locale
import com.example.mytrainer.database.remote.Query as remote

class CreateTrainingDayFragment : Fragment() {
    private var day: Int = -1
    private var listExercise = locale.getInstance().getAllExercises()
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
            createTrainingExercise()
        }
    }

    private fun createTrainingExercise() {
        val adapter = ArrayAdapter<String>(
            context,
            R.layout.support_simple_spinner_dropdown_item,
            listExercise.map { exercise -> exercise.id }
        )
        exercise_name.adapter = adapter
        exercise_series.setText("0")
        exercise_reps.setText("0")
        exercise_recovery_time.setText("0")

        create_exercise.setOnClickListener {
            input_exercise.visibility = View.GONE
            val id = listExercise[exercise_name.selectedItemPosition].id
            val series = exercise_series.text.toString().toInt()
            val reps = exercise_reps.text.toString().toInt()
            val recoveryTime = exercise_recovery_time.text.toString().toInt()
            val exercise = TrainingExercise(day, series, reps, recoveryTime).withId(id)
            println(exercise)
            exercises.add(exercise)
            list.adapter = ExercisesAdapter(exercises, context)
        }
        input_exercise.visibility = View.VISIBLE
    }

    companion object {
        const val DAY = "day"
    }

}