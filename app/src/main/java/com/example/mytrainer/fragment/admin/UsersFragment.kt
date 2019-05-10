package com.example.mytrainer.fragment.admin


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytrainer.R
import com.example.mytrainer.adapter.UsersAdapter
import com.example.mytrainer.component.User
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : Fragment() {
    private var listener: ProfileListener? = null
    var users = emptyList<User>()
    var positionInViewPager = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = UsersAdapter(users, context)

        list.setOnItemClickListener { parent, v, position, id ->
            println("item clicked $id ${users[position]} in $positionInViewPager")
            listener?.view(users[position], positionInViewPager)
        }

        list.setOnItemLongClickListener { parent, _, position, id ->
            println("item long clicked $id ${users[position]}")
            true
        }
    }

    interface ProfileListener {
        fun view(user: User, fromList: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ProfileListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement ProfileListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}