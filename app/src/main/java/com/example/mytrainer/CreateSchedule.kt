package com.example.mytrainer

import com.example.mytrainer.component.ScheduleRequest
import com.example.mytrainer.component.TrainingExercise
import com.example.mytrainer.component.TrainingSchedule
import com.example.mytrainer.component.User
import java.util.*
import com.example.mytrainer.database.locale.Query as local
import com.example.mytrainer.database.remote.Query as firebase

class CreateSchedule {

    fun addSchedule() {
        val trainingSchedule = TrainingSchedule(
            getTrainer(),
            getAthlete(),
            Date(),
            getExercises()
        )

        firebase.addTrainingSchedule(trainingSchedule) { schedule ->
            local.getInstance().addTrainingSchedule(schedule)
        }
    }

    fun addRequest() {
        val scheduleRequest = ScheduleRequest(
            getAthlete(),
            getTrainer(),
            "richiesta"
        )

        firebase.addScheduleRequest(scheduleRequest) { request ->
            local.getInstance().addScheduleRequest(request)
        }
    }

    private fun getAthlete(): User {
        return local.getInstance().getUser()
    }

    private fun getTrainer(): User {
        val trainer = User().withId("rT0ynh5F8wNNOEGgjS77YVu13NM2")
        trainer.firstName = "Ivan"
        trainer.lastName = "Lamperti"
        trainer.type = "Trainer"

        return trainer
    }

    private fun getExercises(): List<TrainingExercise> {
        val exercises = ArrayList<TrainingExercise>()

        //Day 1
        exercises.add(newExercise("Chest Press", "Pettorali", 1, 3, 10, 90))
        exercises.add(newExercise("Croci ai cavi", "Pettorali", 1, 3, 15, 90))
        exercises.add(newExercise("Distensioni su panca inclinata alla smith machine", "Pettorali", 1, 3, 20, 90))
        exercises.add(newExercise("Bicipiti alla macchina (arm curl)", "Bicipiti", 1, 4, 7, 120))
        exercises.add(newExercise("Spider curl con manubrio", "Bicipiti", 1, 4, 12, 120))

        //Day 2
        exercises.add(newExercise("Stacchi da terra", "Dorsali", 2, 5, 12, 60))
        exercises.add(newExercise("Trazioni alla sbarra (chin up)", "Dorsali", 2, 2, 15, 60))
        exercises.add(newExercise("Pullover ai cavi", "Dorsali", 2, 3, 12, 60))
        exercises.add(newExercise("Panca presa stretta", "Tricipiti", 2, 6, 15, 60))
        exercises.add(newExercise("Macchina per i dip", "Tricipiti", 2, 6, 12, 60))

        //Day 3
        exercises.add(newExercise("Military press", "Spalle", 3, 7, 8, 90))
        exercises.add(newExercise("Shrug con manubri", "Spalle", 3, 8, 7, 90))

        //Day 4
        exercises.add(newExercise("Arnold press", "Spalle", 4, 4, 14, 180))
        exercises.add(newExercise("Squat", "Gambe", 4, 6, 78, 145))

        //Day 5
        exercises.add(newExercise("Leg Extension", "Gambe", 5, 3, 11, 140))


        return exercises
    }

    private fun newExercise(
        id: String,
        type: String,
        day: Int,
        series: Int,
        reps: Int,
        recoveryTime: Int
    ): TrainingExercise {
        val exercise = TrainingExercise().withId(id)
        exercise.types = listOf(type)
        exercise.day = day
        exercise.series = series
        exercise.reps = reps
        exercise.recoveryTime = recoveryTime

        return exercise
    }

}