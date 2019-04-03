package com.example.mytrainer

import com.example.mytrainer.component.Exercise
import java.io.File

class Test {
    fun esercizi() {
        spalle()
    }

    private fun spalle() {
        val list: MutableList<Exercise> = mutableListOf()
        println("Fanculo")
        var exercise = Exercise("", "Spalle", File("images/military_press.jpg").readBytes())
        exercise.id = "Military press"
        println(exercise)
    }
}