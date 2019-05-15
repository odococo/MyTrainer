package com.example.mytrainer.fragment.admin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.mytrainer.R
import com.example.mytrainer.adapter.admin.AllUsers
import kotlinx.android.synthetic.main.fragment_manage_users.*

class ManageUsersFragment : Fragment() {
    private var list = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list = it.getInt(LIST, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_users, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewpager.adapter = AllUsers(context)
        sliding_tabs.setupWithViewPager(viewpager)
        viewpager.currentItem = list
    }

    companion object {
        const val LIST = "list"
    }
}
