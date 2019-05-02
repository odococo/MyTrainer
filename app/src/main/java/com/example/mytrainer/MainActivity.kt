package com.example.mytrainer

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.mytrainer.utils.FragmentManager
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import com.example.mytrainer.database.locale.Query as LocalDB
import com.example.mytrainer.database.remote.Query as remoteDB
import com.example.mytrainer.adapter.FragmentAdapter
import com.example.mytrainer.fragment.ExerciseListFragment
import com.example.mytrainer.fragment.main.HomeFragment
import com.example.mytrainer.fragment.main.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : GeneralActivity("MainActivity") {

    private val LAYOUT: Int = R.layout.activity_main
    private lateinit var contentManager: FragmentManager
    private lateinit var localDB: LocalDB


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefaul)
        super.onCreate(savedInstanceState)

        localDB = LocalDB.getInstance(this)
        contentManager = FragmentManager(this, R.id.content_layout, HomeFragment())

        setContentView(LAYOUT)
        initToolbar()
        initNavigationView()
        initPager()

        //Test().esercizi()
        //localDB.clearAndRestoreDB()
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
                    tabLayout.visibility = View.GONE
                    contentManager.switch(ProfileFragment())
                    toolbar.title = "Profile"
                    true
                }
                R.id.currentScheduleItem -> {
                    // TODO fragment di base per una scheda. All'interno avra' l'adapter
                    true
                }
                R.id.scheduleHistoryItem -> {
                    // TODO stesso discorso di sopra. Eventualmente si potrebbe utilizzare un container/layout che contiene il fragment sopra
                    /*val adapter = ScheduleHistoryAdapter()
                    val fragment: MutableMap<Int, GeneralFragment> = HashMap()
                    fragment[0] = GeneralFragment.getInstance(applicationContext, adapter, "")
                    FragmentsActivity.fragment = fragment

                    val intent: Intent = Intent(applicationContext, FragmentsActivity::class.java)
                    intent.putExtra("toolBarName", R.string.schedule_history)
                    startActivity(intent)*/
                    true
                }
                R.id.requestScheduleItem -> {
                    /*val adapter = RequestScheduleAdapter()
                    val fragment: MutableMap<Int, GeneralFragment> = HashMap()
                    fragment[0] = GeneralFragment.getInstance(applicationContext, adapter, "")
                    FragmentsActivity.fragment = fragment

                    val intent: Intent = Intent(applicationContext, FragmentsActivity::class.java)
                    intent.putExtra("toolBarName", R.string.request_schedule)
                    startActivity(intent)*/
                    true
                }
                R.id.helpItem -> {
                    /*val adapter = HelpAdapter()
                    val fragment: MutableMap<Int, GeneralFragment> = HashMap()
                    fragment[0] = GeneralFragment.getInstance(applicationContext, adapter, "")
                    FragmentsActivity.fragment = fragment

                    val intent: Intent = Intent(applicationContext, FragmentsActivity::class.java)
                    intent.putExtra("toolBarName", R.string.help)
                    startActivity(intent)*/
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

    //Qui avviene l'inizializzazione dei pager
    private fun initPager() {
        val tabs: MutableMap<Int, Fragment> = HashMap()

        tabs[0] = ExerciseListFragment.getInstance(applicationContext, "Giorno 1")
        tabs[1] = ExerciseListFragment.getInstance(applicationContext, "Giorno 2")
        tabs[2] = ExerciseListFragment.getInstance(applicationContext, "Giorno 3")

        val adapter = FragmentAdapter(tabs, supportFragmentManager)
        viewPager?.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
