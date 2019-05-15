package com.example.mytrainer.fragment.trainer


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.mytrainer.R
import kotlinx.android.synthetic.main.fragment_create_general.*

class CreateGeneralFragment : Fragment() {
    private var listener: CreateGeneralListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_general, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

}
