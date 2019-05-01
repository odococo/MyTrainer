package com.example.mytrainer.fragment.login

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytrainer.GeneralActivity

import com.example.mytrainer.R
import com.example.mytrainer.database.locale.Query
import kotlinx.android.synthetic.main.fragment_loading.*

class LoadingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    fun init() {
        info.text = getString(R.string.init_db)
        Query.getInstance(context).init(this)
    }

    fun joke() {
        info.text = getString(R.string.joke)
        Handler().postDelayed(
            {
                (context as GeneralActivity).auth.toHome()
            },
            5000
        )
    }
}
