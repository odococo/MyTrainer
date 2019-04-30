package com.example.mytrainer

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import com.example.mytrainer.database.locale.Query
import com.example.mytrainer.fragment.ProfileFragment
import com.example.mytrainer.utils.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : GeneralActivity("MainActivity") {

    private val LAYOUT: Int = R.layout.activity_main
    private lateinit var contentManager: FragmentManager
    private lateinit var db: Query

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefaul)
        super.onCreate(savedInstanceState)

        db = Query.getInstance()
        contentManager = FragmentManager(this, R.id.content_layout)

        setContentView(LAYOUT)
        initToolbar()
        initNavigationView()
        initPager()

        //Test().esercizi()
        //db.clearAndRestoreDB()
        println(db.getExercise("Shoulder press"))
        println(db.getUser())

    }

    private fun initToolbar() {
        mainToolbar.setTitle(R.string.app_name)
        mainToolbar?.setOnMenuItemClickListener {
            println(it)
            true
        }
        mainToolbar.inflateMenu(R.menu.menu_toolbar)
    }

    private fun initNavigationView() {
        val toolBarToogle = ActionBarDrawerToggle(
            this,
            main_drawer_layout,
            mainToolbar,
            R.string.view_navigation_open,
            R.string.view_navigation_close
        )

        main_drawer_layout.addDrawerListener(toolBarToogle)
        toolBarToogle.syncState()

        mainNavigation.setNavigationItemSelectedListener { scelta ->
            main_drawer_layout.closeDrawers()
            when (scelta.itemId) {
                R.id.profileItem -> {
                    //Nasconde il tab layout nel Profile fragment
                    mainTabLayout.visibility = View.GONE
                    contentManager.switch(ProfileFragment())
                    mainToolbar.title = "Profile"
                    true
                }
                R.id.currentScheduleItem -> {
                    // TODO fragment di base per una scheda. All'interno avra' l'adapter
                    true
                }
                R.id.scheduleHistoryItem -> {
                    // TODO stesso discorso di sopra. Eventualmente si potrebbe utilizzare un container/layout che contiene il fragment sopra
                    /*val adapter = ScheduleHistoryAdapter()
                    val fragment: MutableMap<Int, GeneralFragment> = HashMap<Int, GeneralFragment>()
                    fragment[0] = GeneralFragment.getInstance(applicationContext, adapter, "")
                    FragmentsActivity.fragment = fragment

                    val intent: Intent = Intent(applicationContext, FragmentsActivity::class.java)
                    intent.putExtra("toolBarName", R.string.schedule_history)
                    startActivity(intent)*/
                    true
                }
                R.id.requestScheduleItem -> {
                    /*val adapter = RequestScheduleAdapter()
                    val fragment: MutableMap<Int, GeneralFragment> = HashMap<Int, GeneralFragment>()
                    fragment[0] = GeneralFragment.getInstance(applicationContext, adapter, "")
                    FragmentsActivity.fragment = fragment

                    val intent: Intent = Intent(applicationContext, FragmentsActivity::class.java)
                    intent.putExtra("toolBarName", R.string.request_schedule)
                    startActivity(intent)*/
                    true
                }
                R.id.helpItem -> {
                    /*val adapter = HelpAdapter()
                    val fragment: MutableMap<Int, GeneralFragment> = HashMap<Int, GeneralFragment>()
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
       /* val exerciseList: ExerciseListAdapter = ExerciseListAdapter()
        val tabs: MutableMap<Int, GeneralFragment> = HashMap()

        //tabs[0] = GeneralFragment.getInstance(applicationContext, exerciseList, "Giorno 1")

        val adapter = FragmentAdapter(tabs, supportFragmentManager)
        mainViewPager?.adapter = adapter
        mainTabLayout.setupWithViewPager(mainViewPager)
        */
    }
}
