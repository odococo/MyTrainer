package com.example.mytrainer

import android.os.Bundle
import com.example.mytrainer.adapter.ItemsFragmentAdapter
import com.example.mytrainer.fragment.GeneralFragment
import kotlinx.android.synthetic.main.activity_fragments.*

class FragmentsActivity() : GeneralActivity("FragmentsActivity") {

    companion object{
        lateinit var fragment: GeneralFragment
    }

    private var LAYOUT:Int = R.layout.activity_fragments

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppDefaul)
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)

        val b: Bundle = intent.extras
        initToolbar(b.getInt("toolBarName"))

        initItemPager(fragment)
    }

    private fun initToolbar(name: Int) {
        fragmentToolbar.setTitle(name)
        fragmentToolbar?.setOnMenuItemClickListener {
            when (it) {

            }
        }

        fragmentToolbar.inflateMenu(R.menu.menu_toolbar)
        setSupportActionBar(fragmentToolbar);

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        fragmentToolbar.setNavigationOnClickListener {
            finish()
        }

    }

    private fun initItemPager(fragment: GeneralFragment) {
        val adapter = ItemsFragmentAdapter(fragment, supportFragmentManager)
        fragmentViewPager?.adapter = adapter

    }
}
