package com.divyang.thesportsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.divyang.thesportsapp.R
import com.divyang.thesportsapp.ui.search.SearchFragment
import com.divyng.thesportapp.ui.favorites.FavoritesFragment

import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            setFragment(SearchFragment.newInstance())
        }

        nav_bottom.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_search -> setFragment(SearchFragment.newInstance())
                R.id.action_settings -> setFragment(FavoritesFragment.newInstance())
            }
            return@setOnNavigationItemSelectedListener true
        }

    }
}
