package io.rot.labs.projectconf

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.BackoffPolicy
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import io.rot.labs.projectconf.background.work.AllEventsFetchWorker
import io.rot.labs.projectconf.background.work.UserTopicBiWeeklyAlertWorker
import io.rot.labs.projectconf.background.work.UserTopicNewAlertsWorker
import io.rot.labs.projectconf.data.local.prefs.ThemePreferences
import io.rot.labs.projectconf.di.component.ApplicationComponent
import io.rot.labs.projectconf.di.component.DaggerApplicationComponent
import io.rot.labs.projectconf.di.module.ApplicationModule
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ConfApplication : Application(), Configuration.Provider {

    lateinit var appComponent: ApplicationComponent

    @Inject
    lateinit var workerConfiguration: Configuration

    @Inject
    lateinit var themePreferences: ThemePreferences

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
        initTheme()
        scheduleWorkers(AllEventsFetchWorker::class.java, AllEventsFetchWorker.TAG, 4, 7)
        scheduleWorkers(
            UserTopicNewAlertsWorker::class.java,
            UserTopicNewAlertsWorker.TAG,
            2,
            7
        )
        scheduleWorkers(
            UserTopicBiWeeklyAlertWorker::class.java,
            UserTopicBiWeeklyAlertWorker.TAG,
            3,
            15
        )
    }

    private fun injectDependencies() {
        appComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()

        appComponent.inject(this)
    }

    private fun scheduleWorkers(
        workerClass: Class<out ListenableWorker>,
        tag: String,
        initialDelay: Long,
        repeatInterval: Long
    ) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest =
            PeriodicWorkRequest.Builder(workerClass, repeatInterval, TimeUnit.DAYS)
                .setInitialDelay(initialDelay, TimeUnit.MINUTES)
                .setBackoffCriteria(BackoffPolicy.LINEAR, 1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                tag,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }

    fun setComponent(appComponent: ApplicationComponent) {
        this.appComponent = appComponent
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return workerConfiguration
    }

    private fun initTheme() {
        val mode = themePreferences.getThemeMode()
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}
