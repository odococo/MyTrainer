package com.example.mytrainer

//import kotlinx.android.synthetic.main.activity_home.*
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.mytrainer.component.*
import kotlinx.android.synthetic.main.activity_home_test.*

class HomeActivity : GeneralActivity("Home"), Toolbar.OnMenuItemClickListener {

    private val LAYOUT: Int = R.layout.activity_home_test

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefaul)
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)

        initToolbar()
        initNavigationView()

        logout.setOnClickListener {
            auth.logout()
        }

        println(objToTable(User(), "Users"))
        println(objToTable(Exercise(), "Exercises"))
        println(objToTable(TrainingExercise(), "TrainingExercises"))
        println(objToTable(TrainingSchedule(), "TrainingSchedule"))
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.app_name)
        toolbar.setOnMenuItemClickListener(this)
        toolbar.inflateMenu(R.menu.menu_toolbar)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return false
    }

    private fun initNavigationView() {
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
                R.id.actionNotificationItem -> {
                    true
                }

                else -> true
            }

        }
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
