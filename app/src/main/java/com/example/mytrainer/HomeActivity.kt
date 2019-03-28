package com.example.mytrainer

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.mytrainer.auth.Auth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : GeneralActivity("Home"), Toolbar.OnMenuItemClickListener {

    private val LAYOUT: Int = R.layout.activity_home_test
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefaul)
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)

        initToolbar()
        initNavigationView()

      /*  logout.setOnClickListener {
           Auth(this).logout()
        }
      */
    }

    private fun initToolbar(){
        this.toolbar = findViewById(R.id.toolbar)
        this.toolbar?.setTitle(R.string.app_name)
        this.toolbar?.setOnMenuItemClickListener(this)
        this.toolbar?.inflateMenu(R.menu.menu_toolbar)
    }
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return false
    }

    private fun initNavigationView() {
        val drawerLayoyt: DrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toogle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayoyt, this.toolbar, R.string.view_navigation_open, R.string.view_navigation_close)

        drawerLayoyt.addDrawerListener(toogle)
        toogle.syncState()

        var navigationView = findViewById<NavigationView>(R.id.navigation)
        navigationView.setNavigationItemSelectedListener{
            drawerLayoyt.closeDrawers()
            when(it.itemId){
                R.id.actionNotificationItem -> {
                    true
                }

                else -> true
            }

        }
    }

}
