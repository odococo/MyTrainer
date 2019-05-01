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

class ExerciseListFragment: Fragment() {

    private val LAYOUT: Int = R.layout.fragment_general
    private lateinit var externalContext: Context
    private lateinit var title: String

    companion object{

        fun getInstance(context: Context, title: String): ExerciseListFragment{

            val fragment = ExerciseListFragment()
            fragment.externalContext = context
            fragment.title = title

            return fragment
        }
    }

    fun getTitle(): String{
        return this.title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(LAYOUT, container, false)

        val rs: RecyclerView = view.findViewById(R.id.recycle_view)
        rs.layoutManager = LinearLayoutManager(externalContext)
        rs.adapter = ExerciseListAdapter()

        return view
    }


}