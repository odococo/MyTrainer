package com.example.mytrainer.fragment.admin


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import com.example.mytrainer.Codes
import com.example.mytrainer.R
import com.example.mytrainer.adapter.UserAdapter
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
        list.adapter = UserAdapter(users, context)

        list.setOnItemClickListener { _, _, position, _ ->
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
                    menu?.add(0, Codes.SwitchTO.TRAINER, 1, getString(R.string.update_to_trainer))
                    menu?.add(0, Codes.SwitchTO.ADMIN, 2, getString(R.string.update_to_admin))
                }
                "trainer" -> {
                    menu?.add(0, Codes.SwitchTO.ATHLETE, 1, getString(R.string.update_to_athlete))
                    menu?.add(0, Codes.SwitchTO.ADMIN, 2, getString(R.string.update_to_admin))
                }
                else -> {
                    menu?.add(0, Codes.SwitchTO.ATHLETE, 1, getString(R.string.update_to_athlete))
                    menu?.add(0, Codes.SwitchTO.TRAINER, 2, getString(R.string.update_to_trainer))
                }
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val user = list.getItemAtPosition((item?.menuInfo as AdapterView.AdapterContextMenuInfo).position) as User
        val type = when (item.itemId) {
            Codes.SwitchTO.ATHLETE -> "athlete"
            Codes.SwitchTO.TRAINER -> "trainer"
            Codes.SwitchTO.ADMIN -> "admin"
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