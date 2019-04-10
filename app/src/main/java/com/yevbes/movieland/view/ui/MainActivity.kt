package com.yevbes.movieland.view.ui

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.yevbes.movieland.R
import com.yevbes.movieland.utils.ConstantManager

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private var doubleBackToExitPressedOnce = false
    private var currentDrawerItemID: Int = 0

    private val topRatedMoviesFragment: TopRatedMoviesFragment by lazy {
        TopRatedMoviesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupViews()
        if (savedInstanceState == null) {
            loadFragment(ConstantManager.ACTION_TOP_RATED_MOVIES)
            navigationView.menu.findItem(R.id.nav_top_rated_movies).isChecked = true

            title = resources.getString(R.string.action_top_rated_movies)
        }else{
            title = savedInstanceState.getCharSequence(ConstantManager.KEY_STATE_TITLE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putCharSequence(ConstantManager.KEY_STATE_TITLE, title)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        title = savedInstanceState?.getCharSequence(ConstantManager.KEY_STATE_TITLE)
    }

    private inline fun <T : View> T.postEx(crossinline callback: T.() -> Unit) = post { callback() }
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        drawerLayout.postEx { closeDrawer(GravityCompat.START) }
        when (p0.itemId) {
            R.id.nav_top_rated_movies -> {
                if (currentDrawerItemID != ConstantManager.ACTION_TOP_RATED_MOVIES) {
                    loadFragment(ConstantManager.ACTION_TOP_RATED_MOVIES)
                }
            }

            R.id.nav_favorite -> {
                if (currentDrawerItemID != ConstantManager.ACTION_FAVORITE_MOVIES) {
                    loadFragment(ConstantManager.ACTION_FAVORITE_MOVIES)
                }
            }
        }
        p0.isChecked = true
        title = p0.title
        return true
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    private fun setupViews() {

        toolbar = findViewById(R.id.tb)
        drawerLayout = findViewById(R.id.dl)
        navigationView = findViewById(R.id.nv)

        setSupportActionBar(toolbar)
        setTitle(R.string.app_name)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        navigationView.setNavigationItemSelectedListener(this)

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, getString(R.string.double_back_to_exit), Toast.LENGTH_SHORT).show()

            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }

    private fun loadFragment(currentDrawerItemID: Int) {
        clearBackStack()
        this.currentDrawerItemID = currentDrawerItemID
        when (currentDrawerItemID) {
            ConstantManager.ACTION_TOP_RATED_MOVIES -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        topRatedMoviesFragment,
                        getString(R.string.action_top_rated_movies)
                    )
                    .commit()

            }

            ConstantManager.ACTION_FAVORITE_MOVIES -> {

            }
        }
    }

    private fun clearBackStack() {
        val fragmentManager = supportFragmentManager
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStackImmediate()
        }
    }
}
