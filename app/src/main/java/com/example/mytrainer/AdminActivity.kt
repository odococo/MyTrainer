package com.example.mytrainer

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import com.example.mytrainer.component.User
import com.example.mytrainer.fragment.HelpFragment
import com.example.mytrainer.fragment.ProfileFragment
import com.example.mytrainer.fragment.admin.ManageUsersFragment
import com.example.mytrainer.fragment.admin.UsersFragment
import com.example.mytrainer.utils.FragmentManager
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.navigation_header.view.*
import com.example.mytrainer.database.locale.Query as locale
import com.example.mytrainer.database.remote.Query as remote

class AdminActivity : GeneralActivity("Admin"), UsersFragment.UserListener {

    private var fromListUsers = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefaul)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        remote.getAllUsers { users ->
            locale.getInstance().addAll(users)
        }

        manager = FragmentManager(this, R.id.content_layout, ManageUsersFragment())

        setToolbarTitle()
        initNavigationView()

    }

    private fun setToolbarTitle() {
        toolbar.title = when (manager.currentFragment) {
            is ProfileFragment -> "Profilo"
            is ManageUsersFragment -> "Elenco utenti"
            else -> getString(R.string.app_name)
        }
    }

    private fun initNavigationView() {
        val toolBarToogle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.view_navigation_open,
            R.string.view_navigation_close
        )

        drawer_layout.addDrawerListener(toolBarToogle)
        toolBarToogle.syncState()

        mainNavigation.getHeaderView(0).trainerFirstName.text = user.firstName
        mainNavigation.getHeaderView(0).trainerLastName.text = user.lastName

        when (user.type) {
            "admin" -> {
                mainNavigation.menu.add(
                    R.id.switchProfile,
                    Codes.SwitchTO.ATHLETE,
                    mainNavigation.menu.size() + 1,
                    getString(R.string.switch_to_athlete)
                )
                mainNavigation.menu.add(
                    R.id.switchProfile,
                    Codes.SwitchTO.TRAINER,
                    mainNavigation.menu.size() + 1,
                    getString(R.string.switch_to_trainer)
                )
            }
            else -> auth.toHome()
        }

        mainNavigation.setNavigationItemSelectedListener { scelta ->
            drawer_layout.closeDrawers()
            when (scelta.itemId) {
                R.id.profileItem -> {
                    manager.switch(ProfileFragment())
                }
                R.id.allUsers -> {
                    manager.switch(ManageUsersFragment())
                }
                R.id.helpItem -> {
                    manager.switch(HelpFragment())
                }
                R.id.logoutItem -> {
                    auth.logout()
                }
                R.id.cleanItem -> {
                    locale.getInstance().clearAndRestoreDB()
                }
                Codes.SwitchTO.ATHLETE -> {
                    auth.toHome()
                }
                Codes.SwitchTO.TRAINER -> {
                    auth.toTrainer()
                }
            }
            fromListUsers = -1
            setToolbarTitle()
            true
        }
    }

    override fun view(user: User, fromList: Int) {
        fromListUsers = fromList
        manager.switch(ProfileFragment(), mapOf(ProfileFragment.USER to user.id))
    }

    override fun reload(fromList: Int) {
        manager.switch(ManageUsersFragment(), mapOf(ManageUsersFragment.LIST to fromListUsers))
    }

    override fun onBackPressed() {
        if (fromListUsers != -1) {
            manager.switch(ManageUsersFragment(), mapOf(ManageUsersFragment.LIST to fromListUsers))
            fromListUsers = -1
        } else {
            super.onBackPressed()
        }
    }

}
