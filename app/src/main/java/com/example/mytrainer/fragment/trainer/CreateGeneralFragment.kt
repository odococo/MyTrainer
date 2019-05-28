package com.example.mytrainer.fragment.trainer


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.mytrainer.R
import com.example.mytrainer.component.User
import com.example.mytrainer.database.locale.Query
import kotlinx.android.synthetic.main.fragment_create_general.*

class CreateGeneralFragment : Fragment() {
    private var listener: CreateGeneralListener? = null
    private var athlete = User()
    private var info = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            athlete = Query.getInstance().getUserById(it.getString(ATHLETE, ""))
            info = it.getString(INFO, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_general, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        request_athlete.text = context.getString(R.string.request_athlete).format(athlete.name())
        request_info.text = context.getString(R.string.request_info).format(info)

        complete.setOnClickListener {
            listener?.complete()
        }
    }

    interface CreateGeneralListener {
        fun complete()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CreateGeneralListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement CreateGeneralListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        const val ATHLETE = "athlete"
        const val INFO = "info"
    }

}
