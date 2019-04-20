package com.example.mytrainer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mytrainer.adapter.ProfileAdapter
import com.example.mytrainer.adapter.ProfileTabAdapter
import kotlinx.android.synthetic.main.activity_fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class FragmentsActivity : AppCompatActivity() {

    private var LAYOUT:Int = R.layout.activity_fragments

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefaul)
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)

        initToolbar()
        initTabs()

    }
    private fun initToolbar() {
        fragmentToolbar.setTitle(R.string.app_name)
        fragmentToolbar?.setOnMenuItemClickListener {
            when (it) {

            }
        }
        fragmentToolbar.inflateMenu(R.menu.menu_toolbar)

    }

    private fun initTabs() {
        val adapter = ProfileTabAdapter(applicationContext, supportFragmentManager)
        fragmentViewPager?.adapter = adapter
        fragmentTabLayout.setupWithViewPager(fragmentViewPager)
    }
}
