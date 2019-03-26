package com.example.mytrainer

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.mytrainer.auth.Auth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : GeneralActivity("Home"), Toolbar.OnMenuItemClickListener {

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initToolbar()

        logout.setOnClickListener {
            Auth(this).logout()
        }
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
}
