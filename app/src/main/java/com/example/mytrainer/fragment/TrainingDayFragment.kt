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
import com.example.mytrainer.component.TrainingExercise

class TrainingDayFragment: Fragment() {

    private val LAYOUT:Int = R.layout.training_day_fragment
    private var anotherView: View? = null

    var title: String = "" //Il titolo del tab che corrisponde ad un giorno di allenamento

    companion object {

        //Questo è il contesto passatto dal'esterno, che è diverso da quello in cui si trova la seguente classe.
        //Server per ricavare il layout ed il recycle view.
        private var externalContext: Context? = null
        private var daysList: List<TrainingExercise>? = null

        //TODO ricevere come argomento una lista che corrisponde ad un giorno contenente tutti gli esercizi da fare.
        //Sarebe al posto del titolo, esso deve essere già contenuto all'interno del giorno di allenamento.
        fun getInstance(context: Context, title: String): TrainingDayFragment{
            externalContext = context

            var args: Bundle = Bundle()
            var daysFragment: TrainingDayFragment = TrainingDayFragment()

            daysFragment.arguments = args

            //Setting del nome del tab(nome del giorno). Che sarebbe: "Giorno 1", "Giorno 2" etc.
            //Oppure si può mettere: Giorno: Spalle + tricipiti e cose del genere
            daysFragment.title = title

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

    //TODO estrarre tutti gli esercizi di un determinato giorno(passato come argomento nel costruttore della classe).
    //Metterli nella lista e passarli avanti
    private fun createMockData(): List<TrainingExercise> {
        val data: ArrayList<TrainingExercise> = ArrayList()

        data.add(TrainingExercise(3, 15, 90))
        data.add(TrainingExercise(4, 10, 120))
        data.add(TrainingExercise(5, 8, 180))

        return data
    }
}