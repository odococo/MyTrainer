package com.example.mytrainer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytrainer.R
import com.example.mytrainer.component.User
import com.example.mytrainer.database.locale.Query
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : GeneralFragment() {
    private val TAG = "Profile"
    private lateinit var user: User

    // tramite parametri perche' forse vogliamo che tipo gli istruttori vedano il profilo di altri utenti
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "chiamato on create ${arguments.size()}")
        arguments?.let {
            val id = it.getString(USER, "")
            user = if (id.isNotEmpty()) {
                Query.getInstance().getUserById(id)
            } else {
                Query.getInstance().getUser()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileSurname.text = user.lastName
        profileName.text = user.firstName
    }

    companion object {
        const val USER = "user"
    }
}
