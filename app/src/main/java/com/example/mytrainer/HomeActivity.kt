package com.example.mytrainer

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import com.example.mytrainer.adapter.TabsScheduleAdapter
import com.example.mytrainer.component.*
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

        println(objToTable(User(), "Users"))
        println(objToTable(Exercise(), "Exercises"))
        println(objToTable(TrainingExercise(), "TrainingExercises"))
        println(objToTable(TrainingSchedule(), "TrainingSchedule"))
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.app_name)
        toolbar?.setOnMenuItemClickListener{
            when(it){

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
                R.id.currentScheduleItem ->{
                    true
                }
                R.id.scheduleHistoryItem ->{
                    true
                }
                R.id.requestScheduleItem ->{
                    true
                }
                R.id.helpItem ->{
                    true
                }
                R.id.logoutItem ->{
                    auth.logout(); true
                }

                else -> true
            }

        }
    }

    private fun initTabs() {
        val adapter: TabsScheduleAdapter = TabsScheduleAdapter(applicationContext, supportFragmentManager)
        viewPager?.adapter = adapter

        val tabLayout: TabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
    }

    fun objToTable(obj: Component, tablename: String): String {
        val map = obj.toMap()
        val table = "CREATE TABLE ${tablename} (\n"
        val columns = mutableListOf("id TEXT PRIMARY KEY")
        columns.addAll(
            map.filter { entry ->
                when (entry.value) {
                    is List<*> -> false
                    is Component -> false
                    else -> true
                }
            }.map { entry ->
                when (entry.value) {
                    is String -> "${entry.key} TEXT DEFAULT \"${entry.value}\""
                    is Int -> "${entry.key} INTEGER DEFAULT ${entry.value}"
                    is Double -> "${entry.key} REAL DEFAULT ${entry.value}"
                    // TODO blob per immagini
                    else -> throw IllegalArgumentException("$obj ha un tipo (${entry.value.javaClass} non valido!")
                }
            })
        return table + columns.joinToString(",\n") + "\n)"
    }

    fun objToJoin(obj: Component, tablename: String): String {
        val map = obj.toMap()
        return ""
    }

}
