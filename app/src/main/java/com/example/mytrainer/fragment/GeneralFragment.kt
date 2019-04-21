package com.example.mytrainer.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import com.example.mytrainer.R

open class GeneralFragment: Fragment() {

    private val LAYOUT: Int = R.layout.fragment_general
    private lateinit var anotherView: View

    companion object {

        //Questo è il contesto passatto dal'esterno, che è diverso da quello in cui si trova la seguente classe.
        //Server per ricavare il layout ed il recycle view.
        @SuppressLint("StaticFieldLeak")
        private lateinit var externalContext: Context
        private lateinit var adapter: RecyclerView.Adapter<*>

        fun getInstance(context: Context, adapter: RecyclerView.Adapter<*>): GeneralFragment{
            externalContext = context
            this.adapter = adapter

            val args: Bundle = Bundle()
            val daysFragment: GeneralFragment = GeneralFragment()

            daysFragment.arguments = args

            return daysFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        anotherView = inflater.inflate(LAYOUT, container, false)
        val rs: RecyclerView = anotherView.findViewById(R.id.recycle_view)
        rs.layoutManager = LinearLayoutManager(externalContext)
        rs.adapter = adapter
        return anotherView
    }

}