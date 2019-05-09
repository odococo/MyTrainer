package com.example.mytrainer.fragment.admin


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
    var users = emptyList<User>()

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
    }

}