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


        database()
    }

    private fun database() {
        println(objToTable(User(), "Users")) // necessaria per istruttori e atleti e admin
        println(objToTable(Exercise(), "Exercises")) // necessaria  per gli admin
        println(objToTable(TrainingSchedule(), "TrainingSchedule"))
        println(objToTable(TrainingExercise(), "TrainingExercises", listOf(TrainingSchedule())))
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

    fun objToTable(obj: Component, tablename: String, fkeys: List<Component> = emptyList()): String {
        val map = obj.toMap()
        val table = "CREATE TABLE ${tablename}"
        val columns = mutableListOf("id TEXT PRIMARY KEY")
        val foreignKeys = mutableListOf<String>()
        columns.run {
            addAll(
                map.filter { entry ->
                    entry.value !is List<*>
                }.map { entry ->
                    when (entry.value) {
                        is String -> "${entry.key} TEXT DEFAULT \"${entry.value}\""
                        is Int -> "${entry.key} INTEGER DEFAULT ${entry.value}"
                        is Double -> "${entry.key} REAL DEFAULT ${entry.value}"
                        // TODO blob per immagini
                        is Component -> {
                            val referenceTable = entry.value.javaClass.simpleName
                            foreignKeys.add(
                                "CONSTRAINT ${tablename}_$referenceTable " +
                                        "FOREIGN KEY (${entry.key}_id) REFERENCES $referenceTable (id)"
                            )
                            "${entry.key}_id TEXT"
                        }
                        else -> throw IllegalArgumentException("$obj ha un tipo (${entry.value.javaClass} non valido!")
                    }
                })
        }
        val superclass = obj.javaClass.superclass?.simpleName
        if (!superclass.equals("Component")) {
            columns.add("${superclass}_id TEXT")
            foreignKeys.add(
                "CONSTRAINT ${tablename}_$superclass " +
                        "FOREIGN KEY (${superclass}_id) REFERENCES $superclass (id)"
            )
        }
        for (fkey in fkeys) {
            val referenceTable = fkey.javaClass.simpleName
            columns.add("${referenceTable}_id TEXT")
            foreignKeys.add(
                "CONSTRAINT ${tablename}_$referenceTable " +
                        "FOREIGN KEY (${referenceTable}_id) REFERENCES $referenceTable (id)"
            )
        }
        columns.addAll(foreignKeys)
        return table + columns.joinToString(prefix = "(\n", postfix = "\n)", separator = ",\n")
    }

}
