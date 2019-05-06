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
        val exercises = ArrayList<TrainingExercise>()

        //Day 1
        exercises.add(newExsercise("Chest Press","Pettorali", 1, 3, 10, 90))
        exercises.add(newExsercise("Croci ai cavi","Pettorali", 1, 3, 15, 90))
        exercises.add(newExsercise("Distensioni su panca inclinata alla smith machine","Pettorali", 1, 3, 20, 90))
        exercises.add(newExsercise("Bicipiti alla macchina (arm curl)","Bicipiti", 1, 4, 7, 120))
        exercises.add(newExsercise("Spider curl con manubrio","Bicipiti", 1, 4, 12, 120))

        //Day 2
        exercises.add(newExsercise("Stacchi da terra","Dorsali", 2, 5, 12, 60))
        exercises.add(newExsercise("Trazioni alla sbarra (chin up)","Dorsali", 2, 2, 15, 60))
        exercises.add(newExsercise("Pullover ai cavi","Dorsali", 2, 3, 12, 60))
        exercises.add(newExsercise("Panca presa stretta","Tricipiti", 2, 6, 15, 60))
        exercises.add(newExsercise("Macchina per i dip","Tricipiti", 2, 6, 12, 60))

        //Day 2
        exercises.add(newExsercise("Military press","Spalle", 3, 7, 8, 90))
        exercises.add(newExsercise("Shrug con manubri","Spalle", 3, 8, 7, 90))
        exercises.add(newExsercise("Arnold press","Spalle", 3, 4, 14, 180))
        exercises.add(newExsercise("Squat","Gambe", 3, 6, 78, 145))
        exercises.add(newExsercise("Leg Extension","Gambe", 5, 3, 11, 140))


        return exercises
    }

    private fun newExsercise(id: String, type: String, day: Int, series: Int, reps: Int, recoveryTime: Int): TrainingExercise{
        val exercise = TrainingExercise().withId(id)
        exercise.types = listOf(type)
        exercise.day = day
        exercise.series = series
        exercise.reps = reps
        exercise.recoveryTime = recoveryTime

        return exercise
    }

}