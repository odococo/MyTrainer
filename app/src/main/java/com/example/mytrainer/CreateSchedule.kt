package com.example.mytrainer

import android.util.Log
import com.example.mytrainer.component.TrainingExercise
import com.example.mytrainer.component.TrainingSchedule
import com.example.mytrainer.component.User
import com.example.mytrainer.database.remote.Query as firebase
import com.example.mytrainer.database.locale.Query as local
import java.util.*

class CreateSchedule {

    fun addSchedule(){
        val trainingSchedule = TrainingSchedule(
            getAthlete(),
            getTrainer(),
            Date(),
            getExercises()
        )

        firebase.addTrainingSchedule(trainingSchedule)
    }

    private fun getAthlete(): User{
        val athlete = User()
        athlete.firstName = "Anatoliy"
        athlete.lastName = "Roshka"
        athlete.type = "Athlete"

        return athlete
    }

    private fun getTrainer(): User{
        val trainer = User()
        trainer.firstName = "Lamperti"
        trainer.lastName = "Ivan"
        trainer.type = "Trainer"

        return trainer
    }

    private fun getExercises(): List<TrainingExercise>{
        val exercise1 = TrainingExercise()
        exercise1.types = listOf("Military press")
        exercise1.day = 1
        exercise1.series = 3
        exercise1.reps = 10
        exercise1.recoveryTime = 90

        val exercise2 = TrainingExercise()
        exercise1.types = listOf("Stacchi da terra")
        exercise1.day = 1
        exercise1.series = 3
        exercise1.reps = 10
        exercise1.recoveryTime = 90

        val exercise3 = TrainingExercise()
        exercise1.types = listOf("Dorsy Machine")
        exercise1.day = 2
        exercise1.series = 3
        exercise1.reps = 10
        exercise1.recoveryTime = 90

        val exercise4 = TrainingExercise()
        exercise1.types = listOf("Squat")
        exercise1.day = 2
        exercise1.series = 3
        exercise1.reps = 10
        exercise1.recoveryTime = 90

        val exercise5 = TrainingExercise()
        exercise1.types = listOf("Sumo Squat")
        exercise1.day = 3
        exercise1.series = 3
        exercise1.reps = 10
        exercise1.recoveryTime = 90

        val exercise6 = TrainingExercise()
        exercise1.types = listOf("Chest Press")
        exercise1.day = 3
        exercise1.series = 30
        exercise1.reps = 10
        exercise1.recoveryTime = 90


        val exercises = ArrayList<TrainingExercise>()
        exercises.add(exercise1)
        exercises.add(exercise2)
        exercises.add(exercise3)
        exercises.add(exercise4)
        exercises.add(exercise5)
        exercises.add(exercise6)

        return exercises
    }
}