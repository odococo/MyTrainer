package com.example.mytrainer

import android.util.Log
import com.example.mytrainer.component.Exercise
import com.example.mytrainer.database.remote.Query

class Test {
    private val TAG = "Test"

    fun esercizi() {
        spalle()
        dorsali()
        gambe()
        pettorali()
        bicipiti()
        tricipiti()
    }

    // per le descrizioni bisogna cambiare i vari metodi o inserirle successivamente
    fun addExercise(name: String, type: List<String>, description: String = "") {
        val exercise = Exercise(description, type)
        exercise.id = name
        Query.addExercise(exercise)
    }

    private fun spalle() {
        val list: List<String> = listOf(
            "Military press",
            "Alzate laterali",
            "Lento Avanti con bilanciere",
            "Lento Avanti con manubri",
            "Alzate laterali ai cavi",
            "Shoulder press",
            "Lento Dietro con bilanciere",
            "Alzate frontali",
            "Alzate frontali ai cavi",
            "Alzate frontali con bilanciere",
            "Crossover ai cavi inverso",
            "Alzate a 90 gradi",
            "Alzate a 90 gradi ai cavi",
            "Rematore verticale con bilanciere",
            "Rematore verticale con manubri",
            "Shrug con bilanciere",
            "Shrug con manubri",
            "Shrug al multipower",
            "Alzate frontali con manubrio da seduto",
            "Alzate laterali con manubrio da seduto",
            "Panca piana presa inversa",
            "Arnold press",
            "Alzate frontali con corda"
        )
        list.forEach { exercise -> addExercise(exercise, listOf("Spalle")) }
        Log.i(TAG, "Aggiunti ${list.size} esercizi per spalle")
    }

    private fun dorsali() {
        val list: List<String> = listOf(
            "Stacchi da terra",
            "Trazioni alla sbarra (chin up)",
            "Rematore con bilanciere",
            "Rematore con manubrio",
            "Rematore ai cavi (unilaterale o bilaterale)",
            "Pulldown alla lat machine",
            "Pulldown alla lat machine presa inversa",
            "Pulldown alla lat machine con triangolo",
            "Pulldown alla lat machine con trazibar",
            "Pullover con manubrio",
            "Pullover ai cavi",
            "Lateral Pulley (orizzontale o inclinato)",
            "Lateral Pulley (orizzontale o inclinato) presa larga",
            "Lateral Pulley (orizzontale o inclinato) con trazybar",
            "Lateral Pulley con maniglia unilaterale",
            "Lat machine con maniglia",
            "Iperestensioni alla panca romana",
            "Rematore su panca inclinata con manubrio",
            "Rematore su panca inclinata con bilanciere",
            "Trazioni alla sbarra presa inversa (chin up)",
            "Rematore con bilanciere presa inversa",
            "Rematore al multipower",
            "Goodmorning con bilanciere",
            "Dorsy Machine",
            "Nautilus Machine"
        )
        list.forEach { exercise -> addExercise(exercise, listOf("Dorsali")) }
        Log.i(TAG, "Aggiunti ${list.size} esercizi per dorsali")
    }

    private fun gambe() {
        val list: List<String> = listOf(
            "Squat",
            "Pressa 45 gradi",
            "Pressa Orizzontale",
            "Pressa Verticale",
            "Leg Extension",
            "Leg Curl",
            "Stacchi da terra gambe tese",
            "Stacchi da terra gambe tese con manubri",
            "Standing leg curl",
            "Sitting leg curl",
            "Affondi frontali (con manubri)",
            "Affondi laterali (con manubri)",
            "Affondi rumeni (al multipower)",
            "Squat Bulgaro",
            "Front Squat",
            "Hack Squat",
            "Slanci posteriori della gamba",
            "Gluteus Machine",
            "Adductor Machine",
            "Abductor Machine",
            "Ponte per glutei",
            "Slanci laterali della gamba",
            "Calf raises in piedi",
            "Calf raises seduto",
            "Calf raises alla leg press",
            "Calf raises alla multipower",
            "Squat al multipower",
            "Hack Squat con bilanciere",
            "Affondi frontali con bilanciere",
            "Affondi laterali con bilanciere",
            "Jefferson squat",
            "Stacchi da terra con trap bar",
            "Sissy Squat",
            "Sumo Squat"
        )
        list.forEach { exercise -> addExercise(exercise, listOf("Gambe")) }
        Log.i(TAG, "Aggiunti ${list.size} esercizi per gambe")
    }

    private fun pettorali() {
        val list: List<String> = listOf(
            "Distensioni con bilanciere su panca inclinata",
            "Distensioni con bilanciere su panca piana",
            "Distensioni con bilanciere su panca reclinata",
            "Distensioni con manubri su panca inclinata",
            "Distensioni con manubri su panca piana",
            "Distensioni con manubri su panca reclinata",
            "Dip alle parallele per i pettorali",
            "Chest Press",
            "Chest Press Incline",
            "Pectoral Machine",
            "Croci con manubri su panca inclinata",
            "Croci con manubri su panca piana",
            "Croci con manubri su panca reclinata",
            "Croci con manubri around the world",
            "Piegamenti sulle braccia",
            "Croci ai cavi",
            "Croci ai cavi su panca piana",
            "Croci ai cavi su panca inclinata",
            "Distensioni su panca inclinata alla smith machine",
            "Distensioni su panca piana alla smith machine",
            "Distensioni su panca reclinata alla smith machine",
            "Croci ai cavi dal basso",
            "Chest press per il petto basso"
        )
        list.forEach { exercise -> addExercise(exercise, listOf("Pettorali")) }
        Log.i(TAG, "Aggiunti ${list.size} esercizi per pettorali")
    }

    private fun bicipiti() {
        val list: List<String> = listOf(
            "Curl con bilanciere",
            "Curl con manubri",
            "Curl a martello",
            "Curl con bilanciere alla panca Scott",
            "Curl con manubri alla panca Scott",
            "Curl con manubri su panca inclinata",
            "Bicipiti alla macchina (arm curl)",
            "Spider curl con bilanciere",
            "Spider curl con manubrio",
            "Curl ai cavi unilaterali e bilaterali",
            "Curl con bilanciere presa inversa",
            "Curl di concentrazione con manubrio",
            "Curl di concentrazione ai cavi",
            "Curl con corda",
            "Curl a martello frontali",
            "Curl ai cavi da sdraiato",
            "Curl ai cavi sopra la testa",
            "Curl con bilanciere alla panca Scott presa inversa"
        )
        list.forEach { exercise -> addExercise(exercise, listOf("Bicipiti")) }
        Log.i(TAG, "Aggiunti ${list.size} esercizi per bicipiti")
    }

    private fun tricipiti() {
        val list: List<String> = listOf(
            "French Press (o skull crusher)",
            "French Press con manubri",
            "Panca presa stretta",
            "Panca presa stretta al multipower",
            "Flessioni presa stretta per i tricipiti",
            "Estensioni sopra la testa ai cavi da seduto",
            "French press su panca inclinata",
            "French press verticale",
            "Macchina per i dip",
            "Estensioni con manubrio busto flesso",
            "Press per i tricipiti sopra la testa con manubrio"
        )
        list.forEach { exercise -> addExercise(exercise, listOf("Tricipiti")) }
        Log.i(TAG, "Aggiunti ${list.size} esercizi per tricipiti")
    }
}