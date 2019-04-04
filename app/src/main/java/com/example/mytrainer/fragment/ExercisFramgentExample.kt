package com.example.mytrainer.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytrainer.R
import com.example.mytrainer.adapter.DaysListAdapter
import com.example.mytrainer.component.Exercise

class ExercisFramgentExample: Fragment() {

    private val LAYOUT:Int = R.layout.fragment_example
    var title: String = ""
    private var anotherView: View? = null

    companion object {

        //Questo è il contesto passatto dal'esterno, che è diverso da quello in cui si trova la seguente classe
        private var externalContext: Context? = null

        fun getInstance(context: Context, title: String): ExercisFramgentExample{
            externalContext = context

            var args: Bundle = Bundle()
            var fragment: ExercisFramgentExample = ExercisFramgentExample()
            fragment.arguments = args

            //Setting del nome del tab(nome del giorno). Che sarebbe: "Giorno 1", "Giorno 2" etc.
            fragment.title = title

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        anotherView = inflater.inflate(LAYOUT, container, false)

        val rs: RecyclerView = anotherView!!.findViewById(R.id.recycle_view)
        rs.layoutManager = LinearLayoutManager(externalContext)
        rs.adapter = DaysListAdapter(createMockData())

        return anotherView!!
    }

    //Qui si specifica il numero di esercizi e tutti gli eventuali campi relativi.
    private fun createMockData(): List<Exercise> {
        val data: ArrayList<Exercise> = ArrayList()
        data.add(Exercise("Esercizio 1", listOf("Descrizione 1")))
        data.add(Exercise("Esercizio 2", listOf("Descrizione 2")))
        data.add(Exercise("Esercizio 3", listOf("Descrizione 3")))

        return data
    }
}