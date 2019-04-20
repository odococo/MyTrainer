package com.example.mytrainer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import com.example.mytrainer.adapter.ProfileTabAdapter
import kotlinx.android.synthetic.main.activity_fragments.*

class FragmentsActivity : GeneralActivity("FragmentsActivity") {

    private var LAYOUT:Int = R.layout.activity_fragments

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefaul)
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)

        val b: Bundle = intent.extras
        initToolbar(b.getInt("toolBarName"))
       initNavigationView()
        initTabs()

    }

    private fun initToolbar(name: Int) {
        fragmentToolbar.setTitle(name)
        fragmentToolbar?.setOnMenuItemClickListener {
            when (it) {

            }
        }

        fragmentToolbar.inflateMenu(R.menu.menu_toolbar)
        setSupportActionBar(fragmentToolbar);

        //TODO qui è presente la freccia indietro. Dopo ti spiego perchè lo messa, per ora commentta. L'opzione è da valutare.
        /*
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        fragmentToolbar.setNavigationOnClickListener {
            finish()
        }
        */
    }

    private fun initNavigationView() {
        val drawerLayoyt: DrawerLayout = findViewById<DrawerLayout>(R.id.fragment_drawer_layout)
        val toogle = ActionBarDrawerToggle(
            this,
            fragment_drawer_layout,
            fragmentToolbar,
            R.string.view_navigation_open,
            R.string.view_navigation_close
        )

        fragment_drawer_layout.addDrawerListener(toogle)
        toogle.syncState()

        fragmentNavigation.setNavigationItemSelectedListener {
            fragment_drawer_layout.closeDrawers()
            when (it.itemId) {
                R.id.profileItem -> {
                    var intent: Intent = Intent(applicationContext, FragmentsActivity::class.java)
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
        val adapter = ProfileTabAdapter(applicationContext, supportFragmentManager)
        fragmentViewPager?.adapter = adapter
    }
}
