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
import com.example.mytrainer.adapter.ExerciseListAdapter
import com.example.mytrainer.adapter.FragmentAdapter
import com.example.mytrainer.database.locale.Query
import kotlinx.android.synthetic.main.activity_main.*

class GeneralFragment: Fragment() {

    private val LAYOUT: Int = R.layout.fragment_general
    private lateinit var externalContext: Context
    private lateinit var adapter: RecyclerView.Adapter<*>

    companion object{

        fun getInstance(context: Context, adapter: RecyclerView.Adapter<*>): GeneralFragment{

            val fragment = GeneralFragment()
            fragment.externalContext = context
            fragment.adapter = adapter

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(LAYOUT, container, false)

        val rs: RecyclerView = view.findViewById(R.id.recycle_view)
        rs.layoutManager = LinearLayoutManager(externalContext)
        rs.adapter = adapter

        return view
    }


}