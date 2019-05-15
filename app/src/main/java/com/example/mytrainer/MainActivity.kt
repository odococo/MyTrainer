package com.example.mytrainer

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.View
import com.example.mytrainer.adapter.*
import com.example.mytrainer.component.TrainingSchedule
import com.example.mytrainer.fragment.GeneralFragment
import com.example.mytrainer.utils.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_header.view.*
import com.example.mytrainer.database.locale.Query as LocalDB
import com.example.mytrainer.database.remote.Query as remoteDB

class MainActivity : GeneralActivity("MainActivity") {

    private val GONE = "GONE"
    private val VISIBLE = "VISIBLE"
    private val LAYOUT: Int = R.layout.activity_main

    private lateinit var localDB: LocalDB

    private lateinit var currentSchedule: TrainingSchedule
    private lateinit var adapter: FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefaul)
        super.onCreate(savedInstanceState)

        localDB = LocalDB.getInstance()
        currentSchedule = localDB.getCurrentSchedule()
        manager = FragmentManager(this, R.id.content_layout)

        setContentView(LAYOUT)
        initToolbar()
        initNavigationView()
        initPager()

        println(auth.getId())

        //CreateSchedule().addRequest()

        //localDB.clearAndRestoreDB()

        //CreateSchedule().addSchedule()

        //Test().esercizi()
        //println(localDB.getExercise("Shoulder press"))
        //println(localDB.getUser())

    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.app_name)
        toolbar?.setOnMenuItemClickListener {
            println(it)
            true
        }
        toolbar.inflateMenu(R.menu.menu_toolbar)
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
            "trainer" -> mainNavigation.menu.add(
                R.id.switchProfile,
                Codes.SwitchTO.TRAINER,
                mainNavigation.menu.size() + 1,
                getString(R.string.switch_to_trainer)
            )
            "admin" -> {
                mainNavigation.menu.add(
                    R.id.switchProfile,
                    Codes.SwitchTO.TRAINER,
                    mainNavigation.menu.size() + 1,
                    getString(R.string.switch_to_trainer)
                )
                mainNavigation.menu.add(
                    R.id.switchProfile,
                    Codes.SwitchTO.ADMIN,
                    mainNavigation.menu.size() + 1,
                    getString(R.string.switch_to_admin)
                )
            }
        }

        mainNavigation.setNavigationItemSelectedListener { scelta ->
            drawer_layout.closeDrawers()
            when (scelta.itemId) {
                R.id.profileItem -> {
                    changeItemState(GONE)
                    manager.switch(GeneralFragment.getInstance(applicationContext, ProfileAdapter()))
                    toolbar.setTitle(R.string.profile)
                    true
                }
                R.id.currentScheduleItem -> {
                    changeItemState(VISIBLE)
                    //In questo caso torna a visualizzare il contenuto dell'Adapter contenente la scheda corrente.
                    toolbar.setTitle(R.string.app_name)
                    true
                }
                R.id.scheduleHistoryItem -> {
                    changeItemState(GONE)
                    manager.switch(GeneralFragment.getInstance(applicationContext, ScheduleHistoryAdapter()))
                    toolbar.setTitle(R.string.schedule_history)
                    true
                }
                R.id.requestScheduleItem -> {
                    changeItemState(GONE)
                    manager.switch(GeneralFragment.getInstance(applicationContext, RequestScheduleAdapter()))
                    toolbar.setTitle(R.string.request_schedule)
                    true
                }
                R.id.helpItem -> {
                    changeItemState(GONE)
                    manager.switch(GeneralFragment.getInstance(applicationContext, HelpAdapter()))
                    toolbar.setTitle(R.string.help)
                    true
                }
                R.id.logoutItem -> {
                    auth.logout()
                    true
                }

                R.id.cleanItem -> {
                    localDB.clearAndRestoreDB()
                    true
                }

                Codes.SwitchTO.TRAINER -> {
                    auth.toTrainer()
                    true
                }

                Codes.SwitchTO.ADMIN -> {
                    auth.toAdmin()
                    true
                }

                else -> true
            }

        }
    }

    private fun initPager() {
        adapter = FragmentAdapter(applicationContext, currentSchedule, supportFragmentManager)
        viewPager?.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun changeItemState(state: String){
        when(state){
            GONE -> {
                tabLayout.visibility = View.GONE
                viewPager.visibility = View.GONE
                content_layout.visibility = View.VISIBLE
            }
            VISIBLE -> {
                tabLayout.visibility = View.VISIBLE
                viewPager.visibility = View.VISIBLE
                content_layout.visibility = View.GONE
            }
            else->{ Log.d(TAG, "Stato di NavigationViewItem, non riconosciuto!")}
        }
    }
}
