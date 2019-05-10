package com.example.mytrainer.fragment.admin


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import com.example.mytrainer.Codes
import com.example.mytrainer.R
import com.example.mytrainer.adapter.UsersAdapter
import com.example.mytrainer.auth.Auth
import com.example.mytrainer.component.User
import kotlinx.android.synthetic.main.fragment_users.*
import com.example.mytrainer.database.locale.Query as locale
import com.example.mytrainer.database.remote.Query as remote

class UsersFragment : Fragment() {
    private var listener: UserListener? = null
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

        list.setOnItemClickListener { _, _, position, id ->
            listener?.view(users[position], positionInViewPager)
        }

        registerForContextMenu(list)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v?.id == list.id) {
            val user = list.getItemAtPosition((menuInfo as AdapterView.AdapterContextMenuInfo).position) as User
            when (user.type) {
                "athlete" -> {
                    menu?.add(0, Codes.SWITCH_TO_TRAINER.code, 1, "Cambia in Trainer")
                    menu?.add(0, Codes.SWITCH_TO_ADMIN.code, 2, "Cambia in Admin")
                }
                "trainer" -> {
                    menu?.add(0, Codes.SWITCH_TO_ATHLETE.code, 1, "Cambia in Athlete")
                    menu?.add(0, Codes.SWITCH_TO_ADMIN.code, 2, "Cambia in Admin")
                }
                else -> {
                    menu?.add(0, Codes.SWITCH_TO_ATHLETE.code, 1, "Cambia in Athlete")
                    menu?.add(0, Codes.SWITCH_TO_TRAINER.code, 2, "Cambia in Trainer")
                }
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val user = list.getItemAtPosition((item?.menuInfo as AdapterView.AdapterContextMenuInfo).position) as User
        val type = when (item.itemId) {
            Codes.SWITCH_TO_ATHLETE.code -> "athlete"
            Codes.SWITCH_TO_TRAINER.code -> "trainer"
            Codes.SWITCH_TO_ADMIN.code -> "admin"
            else -> user.type
        }

        if (Auth.getInstance().getId() == user.id) {
            Toast.makeText(context, "Non puoi modicare il tuo stato!", Toast.LENGTH_SHORT).show()
        } else {
            user.type = type
            remote.updateUser(user) {
                locale.getInstance().updateUser(it)
                listener?.reload(positionInViewPager)
            }
        }

        return true
    }

    interface UserListener {
        fun view(user: User, fromList: Int)

        fun reload(fromList: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is UserListener) {
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