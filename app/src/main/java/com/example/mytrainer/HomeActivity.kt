package com.example.mytrainer

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import com.example.mytrainer.adapter.TabsScheduleAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : GeneralActivity("Home") {

    private val LAYOUT: Int = R.layout.activity_home
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefaul)
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)

        initToolbar()
        initNavigationView()
        initTabs()

        //Test().esercizi()
        //Query.getInstance(this).clearAndRestoreDB()
        println(db.getExercise("Shoulder press"))
        println(db.getUser())
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.app_name)
        toolbar?.setOnMenuItemClickListener {
            when (it) {

            }
        }
        toolbar.inflateMenu(R.menu.menu_toolbar)
    }

    private fun initNavigationView() {
        val drawerLayoyt: DrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toogle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.view_navigation_open,
            R.string.view_navigation_close
        )

        drawer_layout.addDrawerListener(toogle)
        toogle.syncState()

        navigation.setNavigationItemSelectedListener {
            drawer_layout.closeDrawers()
            when (it.itemId) {
                R.id.profileItem -> {
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
                    auth.logout(); true
                }

                else -> true
            }

        }
    }

    private fun initTabs() {
        val adapter = TabsScheduleAdapter(applicationContext, supportFragmentManager)
        viewPager?.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)
    }

}
