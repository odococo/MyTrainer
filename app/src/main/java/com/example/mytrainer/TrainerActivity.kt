package com.example.mytrainer

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import com.example.mytrainer.component.ScheduleRequest
import com.example.mytrainer.component.User
import com.example.mytrainer.fragment.main.ProfileFragment
import com.example.mytrainer.fragment.trainer.PendingRequestsFragment
import com.example.mytrainer.utils.FragmentManager
import kotlinx.android.synthetic.main.activity_trainer.*

class TrainerActivity : GeneralActivity("Trainer"), PendingRequestsFragment.RequestsListener {
    private lateinit var manager: FragmentManager
    private var fromRequests = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefaul) //Mancava questa riga per i colori
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer)

        manager = FragmentManager(this, R.id.content_layout, PendingRequestsFragment())

        setToolbarTitle()
        initNavigationView()

    }

    private fun setToolbarTitle() {
        toolbar.title = when (manager.currentFragment) {
            is ProfileFragment -> "Profilo"
            is PendingRequestsFragment -> "Richieste"
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

        when (auth.getUser().type) {
            "trainer" -> mainNavigation.menu.add(
                R.id.switchProfile,
                Codes.SwitchTO.ATHLETE,
                mainNavigation.menu.size() + 1,
                getString(R.string.switch_to_athlete)
            )
            "admin" -> {
                mainNavigation.menu.add(
                    R.id.switchProfile,
                    Codes.SwitchTO.ATHLETE,
                    mainNavigation.menu.size() + 1,
                    getString(R.string.switch_to_athlete)
                )
                mainNavigation.menu.add(
                    R.id.switchProfile,
                    Codes.SwitchTO.ADMIN,
                    mainNavigation.menu.size() + 1,
                    getString(R.string.switch_to_admin)
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
                R.id.pendingScheduleRequest -> {
                    manager.switch(PendingRequestsFragment())
                }
                R.id.createSchedule -> {
                    // creazione scheda
                }
                R.id.helpItem -> {
                }
                R.id.logoutItem -> {
                    auth.logout()
                }
                R.id.cleanItem -> {
                    com.example.mytrainer.database.locale.Query.getInstance().clearAndRestoreDB()
                }
                Codes.SwitchTO.ATHLETE -> {
                    auth.toHome()
                }
                Codes.SwitchTO.ADMIN -> {
                    auth.toAdmin()
                }
            }
            setToolbarTitle()
            fromRequests = false
            true
        }
    }

    override fun view(user: User) {
        fromRequests = true
        manager.switch(ProfileFragment(), mapOf(ProfileFragment.USER to user.id))
    }

    override fun create(request: ScheduleRequest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        if (fromRequests) {
            manager.switch(PendingRequestsFragment())
            fromRequests = false
        } else {
            super.onBackPressed()
        }
    }
}
