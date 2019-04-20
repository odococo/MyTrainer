package com.example.mytrainer

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.FragmentActivity
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import com.example.mytrainer.adapter.DaysTabAdapter
import com.example.mytrainer.database.locale.Query
import com.example.mytrainer.fragment.GeneralFragment
import com.example.mytrainer.fragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : GeneralActivity("MainActivity") {

    private val LAYOUT: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefaul)
        super.onCreate(savedInstanceState)

        db = Query.getInstance(this)

        setContentView(LAYOUT)
        initToolbar()
        initNavigationView()
        initTabs()

        //Test().esercizi()
        //db.clearAndRestoreDB()
        println(db.getExercise("Shoulder press"))
        println(db.getUser())

    }

    private fun initToolbar() {
        mainToolbar.setTitle(R.string.app_name)
        mainToolbar?.setOnMenuItemClickListener {
            when (it) {

            }
        }
        mainToolbar.inflateMenu(R.menu.menu_toolbar)
    }

    private fun initNavigationView() {
        val drawerLayoyt: DrawerLayout = findViewById<DrawerLayout>(R.id.main_drawer_layout)
        val toogle = ActionBarDrawerToggle(
            this,
            main_drawer_layout,
            mainToolbar,
            R.string.view_navigation_open,
            R.string.view_navigation_close
        )

        main_drawer_layout.addDrawerListener(toogle)
        toogle.syncState()

        mainNavigation.setNavigationItemSelectedListener {
            main_drawer_layout.closeDrawers()
            when (it.itemId) {
                R.id.profileItem -> {
                    val fragment: GeneralFragment = ProfileFragment.getInstance(applicationContext)
                    FragmentsActivity.fragment = fragment

                    var intent: Intent = Intent(applicationContext, FragmentsActivity()::class.java)
                    intent.putExtra("toolBarName", R.string.profile)
                    startActivity(intent)

                    true
                }
                R.id.currentScheduleItem -> {

                    true
                }
                R.id.scheduleHistoryItem -> {
                    true
                }
                R.id.requestScheduleItem -> {
                    true
                }
                R.id.helpItem -> {
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

    private fun initTabs() {
        val adapter = DaysTabAdapter(applicationContext, supportFragmentManager)
        mainViewPager?.adapter = adapter
        mainTabLayout.setupWithViewPager(mainViewPager)
    }

}
