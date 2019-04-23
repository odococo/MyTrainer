package com.example.mytrainer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import com.example.mytrainer.adapter.*
import com.example.mytrainer.database.locale.Query
import com.example.mytrainer.fragment.GeneralFragment
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("UseSparseArrays")
class MainActivity : GeneralActivity("MainActivity") {

    private val LAYOUT: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefaul)
        super.onCreate(savedInstanceState)

        db = Query.getInstance(this)

        setContentView(LAYOUT)
        initToolbar()
        initNavigationView()
        initFragments()

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
        findViewById<DrawerLayout>(R.id.main_drawer_layout)
        val toolBarToogle = ActionBarDrawerToggle(
            this,
            main_drawer_layout,
            mainToolbar,
            R.string.view_navigation_open,
            R.string.view_navigation_close
        )

        main_drawer_layout.addDrawerListener(toolBarToogle)
        toolBarToogle.syncState()

        mainNavigation.setNavigationItemSelectedListener {
            main_drawer_layout.closeDrawers()
            when (it.itemId) {
                R.id.profileItem -> {
                    val adapter = ProfileAdapter()
                    val fragment: MutableMap<Int, GeneralFragment> = HashMap<Int, GeneralFragment>()
                    fragment[0] = GeneralFragment.getInstance(applicationContext, adapter,"")
                    FragmentsActivity.fragment = fragment

                    val intent: Intent = Intent(applicationContext, FragmentsActivity::class.java)
                    intent.putExtra("toolBarName", R.string.profile)
                    startActivity(intent)
                    true
                }
                R.id.currentScheduleItem -> {

                    true
                }
                R.id.scheduleHistoryItem -> {
                    val adapter = ScheduleHistoryAdapter()
                    val fragment: MutableMap<Int, GeneralFragment> = HashMap<Int, GeneralFragment>()
                    fragment[0] = GeneralFragment.getInstance(applicationContext, adapter,"")
                    FragmentsActivity.fragment = fragment

                    val intent: Intent = Intent(applicationContext, FragmentsActivity::class.java)
                    intent.putExtra("toolBarName", R.string.schedule_history)
                    startActivity(intent)
                    true
                }
                R.id.requestScheduleItem -> {
                    val adapter = RequestScheduleAdapter()
                    val fragment: MutableMap<Int, GeneralFragment> = HashMap<Int, GeneralFragment>()
                    fragment[0] = GeneralFragment.getInstance(applicationContext, adapter, "")
                    FragmentsActivity.fragment = fragment

                    val intent: Intent = Intent(applicationContext, FragmentsActivity::class.java)
                    intent.putExtra("toolBarName", R.string.request_schedule)
                    startActivity(intent)
                    true
                }
                R.id.helpItem -> {
                    val adapter = HelpAdapter()
                    val fragment: MutableMap<Int, GeneralFragment> = HashMap<Int, GeneralFragment>()
                    fragment[0] = GeneralFragment.getInstance(applicationContext, adapter, "")
                    FragmentsActivity.fragment = fragment

                    val intent: Intent = Intent(applicationContext, FragmentsActivity::class.java)
                    intent.putExtra("toolBarName", R.string.help)
                    startActivity(intent)
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

    private fun initFragments() {
        val exerciseList: ExerciseListAdapter = ExerciseListAdapter()
        val tabs: MutableMap<Int, GeneralFragment> = HashMap<Int, GeneralFragment>()
        tabs[0] = GeneralFragment.getInstance(applicationContext, exerciseList, "Giorno 1")
        tabs[1] = GeneralFragment.getInstance(applicationContext, exerciseList, "Giorno 2")
        tabs[2] = GeneralFragment.getInstance(applicationContext, exerciseList, "Giorno 3")
        tabs[3] = GeneralFragment.getInstance(applicationContext, exerciseList, "Giorno 4")

        val adapter = FragmentAdapter(tabs, supportFragmentManager)
        mainViewPager?.adapter = adapter
        mainTabLayout.setupWithViewPager(mainViewPager)
    }
}
