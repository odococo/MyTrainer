package com.example.mytrainer

import android.os.Bundle
import com.example.mytrainer.utils.FragmentManager
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.View
import com.example.mytrainer.adapter.*
import com.example.mytrainer.component.TrainingSchedule
import com.example.mytrainer.component.User
import com.example.mytrainer.database.locale.Query as LocalDB
import com.example.mytrainer.database.remote.Query as remoteDB
import com.example.mytrainer.fragment.GeneralFragment
import com.example.mytrainer.fragment.main.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : GeneralActivity("MainActivity") {

    private val GONE = "GONE"
    private val VISIBLE = "VISIBLE"
    private val LAYOUT: Int = R.layout.activity_main


    private lateinit var contentManager: FragmentManager
    private lateinit var localDB: LocalDB

    private lateinit var currentSchedule: TrainingSchedule

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefaul)
        super.onCreate(savedInstanceState)

        localDB = LocalDB.getInstance(this)
        currentSchedule = localDB.getCurrentSchedule()
        contentManager = FragmentManager(this, R.id.content_layout, HomeFragment())

        setContentView(LAYOUT)
        initToolbar()
        initNavigationView()
        initPager()

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

        mainNavigation.setNavigationItemSelectedListener { scelta ->
            drawer_layout.closeDrawers()
            when (scelta.itemId) {
                R.id.profileItem -> {
                    changeItemState(GONE)
                    contentManager.switch(GeneralFragment.getInstance(applicationContext, ProfileAdapter()))
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
                    contentManager.switch(GeneralFragment.getInstance(applicationContext, ScheduleHistoryAdapter()))
                    toolbar.setTitle(R.string.schedule_history)
                    true
                }
                R.id.requestScheduleItem -> {
                    changeItemState(GONE)
                    contentManager.switch(GeneralFragment.getInstance(applicationContext, RequestScheduleAdapter()))
                    toolbar.setTitle(R.string.request_schedule)
                    true
                }
                R.id.helpItem -> {
                    changeItemState(GONE)
                    contentManager.switch(GeneralFragment.getInstance(applicationContext, HelpAdapter()))
                    toolbar.setTitle(R.string.help)
                    true
                }
                R.id.logoutItem -> {
                    auth.logout()
                    true
                }

                else -> true
            }

        }
    }

    private fun initPager() {
        viewPager?.adapter = FragmentAdapter(applicationContext, currentSchedule.exercises, supportFragmentManager)
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
