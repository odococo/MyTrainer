package com.example.mytrainer.adapter

import android.content.Context
import android.support.v4.app.*
import com.example.mytrainer.component.TrainingExercise
import com.example.mytrainer.fragment.GeneralFragment

class FragmentAdapter(
    appContext: Context,
    exercises: List<TrainingExercise>,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    private val fragments: MutableMap<Int, Fragment> = HashMap()
    private val pageTitles: MutableMap<Int, String> = HashMap()

    private var trainingDays: HashMap<Int, List<TrainingExercise>> = HashMap()

    init {

        //Cerco il massimo per capire quanti giorni di allenamnto ci sono
        var numeroGiorni = 0
        for (i in exercises){
            if(i.day > numeroGiorni)
                numeroGiorni = i.day
        }

        //Verifico per ogni esercizio al quale giorno appartiene.
        //Titti gli esercizi di un determinato giorni vengono messi nell'Array.
        //Array poi viene messo in HashMap in cui ogni posizione corrisponde ad un giorno, ed ogni giorno ha n eserizi.
        var i = 0
        while (i < numeroGiorni) {
            val allExercisesOfDay = ArrayList<TrainingExercise>()
            exercises.forEach { exercise ->
                if(exercise.day == i)
                    allExercisesOfDay.add(exercise)
            }
            trainingDays[i] = allExercisesOfDay
            i++
        }

        //Creo un fragment per ogni giorno e li passo l'adapter, che riceve in input tutti gli esercizi del giorno stesso.
        var n = 0
        while(n in trainingDays){
            fragments[n] = GeneralFragment.getInstance(appContext,ExerciseListAdapter(trainingDays[n]))
            pageTitles[n] = "Giorno ${n+1}"
            n++
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pageTitles[position]
    }

    override fun getItem(position: Int): Fragment? {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

}