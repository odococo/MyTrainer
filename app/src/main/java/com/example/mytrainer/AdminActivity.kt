package com.example.mytrainer

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import com.example.mytrainer.fragment.admin.AdminHomeFragment
import com.example.mytrainer.fragment.admin.ManageUsersFragment
import com.example.mytrainer.fragment.main.ProfileFragment
import com.example.mytrainer.utils.FragmentManager
import kotlinx.android.synthetic.main.activity_admin.*
import com.example.mytrainer.database.locale.Query as locale
import com.example.mytrainer.database.remote.Query as remote

class AdminActivity : GeneralActivity("Admin") {

    private lateinit var manager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        remote.getAllUsers { users ->
            locale.getInstance().addAll(users)
        }
        manager = FragmentManager(this, R.id.content_layout, AdminHomeFragment())

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

        mainNavigation.setNavigationItemSelectedListener { scelta ->
            drawer_layout.closeDrawers()
            when (scelta.itemId) {
                R.id.home -> {
                    manager.switch(AdminHomeFragment())
                }
                R.id.profileItem -> {
                    manager.switch(ProfileFragment())
                }
                R.id.allUsers -> {
                    manager.switch(ManageUsersFragment())
                }
                R.id.helpItem -> {
                }
                R.id.logoutItem -> {
                    auth.logout()
                }
                R.id.cleanItem -> {
                    locale.getInstance().clearAndRestoreDB()
                }
                R.id.switchToAthleteItem -> {
                    auth.to(MainActivity())
                }
                R.id.switchToTrainerItem -> {
                    // switch a trainer
                }
            }
            setToolbarTitle()
            true
        }
    }

}
