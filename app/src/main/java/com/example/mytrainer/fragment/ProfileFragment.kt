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
import com.example.mytrainer.adapter.ProfileAdapter

class ProfileFragment: Fragment(), GeneralFragment {

    private val LAYOUT: Int = R.layout.fragment_general
    private lateinit var anotherView: View

    companion object {

        //Questo è il contesto passatto dal'esterno, che è diverso da quello in cui si trova la seguente classe.
        //Server per ricavare il layout ed il recycle view.
        private lateinit var externalContext: Context

        fun getInstance(context: Context): ProfileFragment{
            externalContext = context

            var args: Bundle = Bundle()
            var daysFragment: ProfileFragment = ProfileFragment()

            daysFragment.arguments = args

            return daysFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        anotherView = inflater.inflate(LAYOUT, container, false)
        val rs: RecyclerView = anotherView.findViewById(R.id.recycle_view)
        rs.layoutManager = LinearLayoutManager(externalContext)
        rs.adapter = ProfileAdapter()
        return anotherView
    }
}