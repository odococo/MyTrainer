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
import com.example.mytrainer.adapter.TrainingDayListAdapter
import com.example.mytrainer.component.Exercise

class TrainingDayFragment: Fragment() {

    private val LAYOUT:Int = R.layout.training_day_fragment
    private var anotherView: View? = null

    var title: String = "" //Il titolo del tab che corrisponde ad un giorno di allenamento

    companion object {

        //Questo è il contesto passatto dal'esterno, che è diverso da quello in cui si trova la seguente classe.
        //Server per ricavare il layout ed il recycle view.
        private var externalContext: Context? = null

        fun getInstance(context: Context, title: String): TrainingDayFragment{
            externalContext = context

            var args: Bundle = Bundle()
            var daysFragment: TrainingDayFragment = TrainingDayFragment()

            daysFragment.arguments = args

            //Setting del nome del tab(nome del giorno). Che sarebbe: "Giorno 1", "Giorno 2" etc.
            //Oppure si può mettere: Giorno: Spalle + tricipiti e cose del genere
            daysFragment.title += title

            return daysFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        anotherView = inflater.inflate(LAYOUT, container, false)

        val rs: RecyclerView = anotherView!!.findViewById(R.id.recycle_view)
        rs.layoutManager = LinearLayoutManager(externalContext)
        rs.adapter = TrainingDayListAdapter(createMockData())

        return anotherView!!
    }

    //Qui si specifica il numero di esercizi e tutti gli eventuali campi.
    private fun createMockData(): List<Exercise> {
        val data: ArrayList<Exercise> = ArrayList()
        data.add(Exercise("Descrizione 1", listOf("Descrizione 1")))
        data.add(Exercise("Descrizione 2", listOf("Descrizione 2")))
        data.add(Exercise("Descrizione 3", listOf("Descrizione 3")))

        return data
    }
}