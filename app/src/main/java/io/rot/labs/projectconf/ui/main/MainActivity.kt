package io.rot.labs.projectconf.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.ActivityComponent
import io.rot.labs.projectconf.ui.alerts.alertsView.AlertsFragment
import io.rot.labs.projectconf.ui.archive.ArchiveActivity
import io.rot.labs.projectconf.ui.base.BaseActivity
import io.rot.labs.projectconf.ui.bookmarks.BookmarksFragment
import io.rot.labs.projectconf.ui.search.SearchActivity
import io.rot.labs.projectconf.ui.settings.SettingsActivity
import io.rot.labs.projectconf.ui.twitter.TwitterFragment
import io.rot.labs.projectconf.ui.upcoming.UpComingFragment
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.drawerLayout
import kotlinx.android.synthetic.main.activity_main.ivSearch
import kotlinx.android.synthetic.main.activity_main.ivSearchContainer
import kotlinx.android.synthetic.main.activity_main.matToolBarMain
import kotlinx.android.synthetic.main.activity_main.navView
import kotlinx.android.synthetic.main.activity_main.tvScreenTitle

class MainActivity : BaseActivity<MainViewModel>() {

    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel

    private var activeFragment: Fragment? = null

    override fun injectDependencies(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun setupView(savedInstanceState: Bundle?) {
        setUpNavigationDrawer()
        ivSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java).apply {
                val years = TimeDateUtils.getConfYearsList()
                putExtra(SearchActivity.YEAR_LIST, arrayListOf(years.last() - 1, years.last()))
            })
        }
    }

    override fun setupObservables() {
        super.setupObservables()

        viewModel.upComingNavigation.observe(this, Observer {
            it.getIfNotHandled()?.let {
                showUpComingFragment()
                tvScreenTitle.text = getString(R.string.upcoming_events)
                ivSearchContainer.isVisible = true
            }
        })

        viewModel.tweetNavigation.observe(this, Observer {
            it.getIfNotHandled()?.let {
                showTwitterFragment()
                tvScreenTitle.text = getString(R.string.nav_tweets)
                ivSearchContainer.visibility = View.INVISIBLE
            }
        })

        viewModel.bookmarksNavigation.observe(this, Observer {
            it.getIfNotHandled()?.let {
                showBookmarksFragment()
                tvScreenTitle.text = getString(R.string.nav_bookmarks)
                ivSearchContainer.visibility = View.INVISIBLE
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
                ivSearchContainer.visibility = View.INVISIBLE
            }
        })

        viewModel.settingsNavigation.observe(this, Observer {
            it.getIfNotHandled()?.let {
                val settingsIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingsIntent)
            }
        })

        mainSharedViewModel.searchClickable.observe(this, Observer {
            ivSearch.isClickable = it
        })
    }

    private fun setUpNavigationDrawer() {
        setSupportActionBar(matToolBarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            matToolBarMain,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_upcomimg -> {
                    viewModel.onUpComingRedirection()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_tweets -> {
                    viewModel.onTweetRedirection()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_alerts -> {
                    viewModel.onAlertsRedirection()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_archive -> {
                    viewModel.onArchiveRedirection()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    false
                }
                R.id.nav_bookmarks -> {
                    viewModel.onBookmarksRedirection()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_settings -> {
                    viewModel.onSettingsRedirection()
                    drawerLayout.closeDrawer(GravityCompat.START)
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
