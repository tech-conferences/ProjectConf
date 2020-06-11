package io.rot.labs.projectconf.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.ActivityComponent
import io.rot.labs.projectconf.ui.alerts.AlertsFragment
import io.rot.labs.projectconf.ui.archive.ArchiveActivity
import io.rot.labs.projectconf.ui.base.BaseActivity
import io.rot.labs.projectconf.ui.bookmarks.BookmarksFragment
import io.rot.labs.projectconf.ui.search.SearchActivity
import io.rot.labs.projectconf.ui.settings.SettingsActivity
import io.rot.labs.projectconf.ui.twitter.TwitterFragment
import io.rot.labs.projectconf.ui.upcoming.UpComingFragment
import kotlinx.android.synthetic.main.activity_main.drawerLayout
import kotlinx.android.synthetic.main.activity_main.ivSearch
import kotlinx.android.synthetic.main.activity_main.materialToolBar
import kotlinx.android.synthetic.main.activity_main.navView
import kotlinx.android.synthetic.main.activity_main.tvScreenTitle

class MainActivity : BaseActivity<MainViewModel>() {

    private var activeFragment: Fragment? = null

    override fun injectDependencies(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun setupView(savedInstanceState: Bundle?) {
        setUpNavigationDrawer()
        ivSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    override fun setupObservables() {
        super.setupObservables()

        viewModel.upComingNavigation.observe(this, Observer {
            it.getIfNotHandled()?.let {
                showUpComingFragment()
                tvScreenTitle.text = getString(R.string.upcoming_events)
                ivSearch.isVisible = true
            }
        })

        viewModel.tweetNavigation.observe(this, Observer {
            it.getIfNotHandled()?.let {
                showTwitterFragment()
                tvScreenTitle.text = getString(R.string.nav_tweets)
                ivSearch.isVisible = false
            }
        })

        viewModel.bookmarksNavigation.observe(this, Observer {
            it.getIfNotHandled()?.let {
                showBookmarksFragment()
                tvScreenTitle.text = getString(R.string.nav_bookmarks)
                ivSearch.isVisible = false
            }
        })

        viewModel.archiveNavigation.observe(this, Observer {
            it.getIfNotHandled()?.let {
                val archiveIntent = Intent(this, ArchiveActivity::class.java)
                startActivity(archiveIntent)
            }
        })

        viewModel.alertsNavigation.observe(this, Observer {
            it.getIfNotHandled()?.let {
                showAlertsFragment()
                tvScreenTitle.text = getString(R.string.nav_alerts)
                ivSearch.isVisible = false
            }
        })

        viewModel.settingsNavigation.observe(this, Observer {
            it.getIfNotHandled()?.let {
                val settingsIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingsIntent)
            }
        })
    }

    private fun setUpNavigationDrawer() {
        setSupportActionBar(materialToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            materialToolBar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_upcomimg -> {
                    viewModel.onUpComingRedirection()
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    true
                }
                R.id.nav_tweets -> {
                    viewModel.onTweetRedirection()
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    true
                }
                R.id.nav_alerts -> {
                    viewModel.onAlertsRedirection()
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    true
                }
                R.id.nav_archive -> {
                    viewModel.onArchiveRedirection()
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    false
                }
                R.id.nav_bookmarks -> {
                    viewModel.onBookmarksRedirection()
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    true
                }
                R.id.nav_settings -> {
                    viewModel.onSettingsRedirection()
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    false
                }
                else -> false
            }
        }
    }

    private fun showUpComingFragment() {
        if (activeFragment is UpComingFragment) {
            return
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment =
            supportFragmentManager.findFragmentByTag(UpComingFragment.TAG) as UpComingFragment?

        if (fragment == null) {
            fragment = UpComingFragment.newInstance()
            fragmentTransaction.add(R.id.container, fragment, UpComingFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) {
            fragmentTransaction.hide(activeFragment as Fragment)
        }

        fragmentTransaction.commit()

        activeFragment = fragment
    }

    private fun showTwitterFragment() {
        if (activeFragment is TwitterFragment) {
            return
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment =
            supportFragmentManager.findFragmentByTag(TwitterFragment.TAG) as TwitterFragment?

        if (fragment == null) {
            fragment = TwitterFragment.newInstance()
            fragmentTransaction.add(R.id.container, fragment, TwitterFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) {
            fragmentTransaction.hide(activeFragment as Fragment)
        }

        fragmentTransaction.commit()

        activeFragment = fragment
    }

    private fun showAlertsFragment() {
        if (activeFragment is AlertsFragment) {
            return
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment =
            supportFragmentManager.findFragmentByTag(AlertsFragment.TAG) as AlertsFragment?

        if (fragment == null) {
            fragment = AlertsFragment.newInstance()
            fragmentTransaction.add(R.id.container, fragment, AlertsFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) {
            fragmentTransaction.hide(activeFragment as Fragment)
        }

        fragmentTransaction.commit()

        activeFragment = fragment
    }

    private fun showBookmarksFragment() {
        if (activeFragment is BookmarksFragment) {
            return
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment =
            supportFragmentManager.findFragmentByTag(BookmarksFragment.TAG) as BookmarksFragment?

        if (fragment == null) {
            fragment = BookmarksFragment.newInstance()
            fragmentTransaction.add(R.id.container, fragment, BookmarksFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) {
            fragmentTransaction.hide(activeFragment as Fragment)
        }

        fragmentTransaction.commit()

        activeFragment = fragment
    }
}
