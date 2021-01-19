/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.di.module

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteException
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.Configuration
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.BuildConfig
import io.rot.labs.projectconf.ConfApplication
import io.rot.labs.projectconf.background.work.ManagerWorkerFactory
import io.rot.labs.projectconf.data.local.db.ConfDatabase
import io.rot.labs.projectconf.data.local.prefs.ThemePreferences
import io.rot.labs.projectconf.data.remote.ConfApi
import io.rot.labs.projectconf.utils.display.ScreenResourcesHelper
import io.rot.labs.projectconf.utils.display.ScreenUtils
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.network.NetworkDBHelperImpl
import io.rot.labs.projectconf.utils.rx.RxSchedulerProvider
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import javax.inject.Singleton

@Module
class ApplicationModule(private val confApplication: ConfApplication) {

    @Provides
    @Singleton
    fun provideApplication() = confApplication

    @Provides
    @Singleton
    fun provideNetworkService() = ConfApi.create(
        BuildConfig.BASE_URL,
        confApplication.cacheDir,
        10 * 1024 * 1024 // 10 mb
    )

    @Provides
    @Singleton
    fun provideConfDatabase(
        migrations: Set<@JvmSuppressWildcards Migration>
    ): ConfDatabase =
        Room.databaseBuilder(confApplication, ConfDatabase::class.java, "events.db")
            .addMigrations(*migrations.toTypedArray())
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @IntoSet
    @Singleton
    fun provideMigrationFrom1To2() = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            migrateFrom1To2EventsTable(database)
            migrateFrom1To2BookmarkedEventsTable(database)
        }
    }

    private fun migrateFrom1To2EventsTable(database: SupportSQLiteDatabase) {
        try {
            val cursor = database.query("SELECT * from events")
            cursor.use {
                if (it.moveToFirst()) {
                    val contentValues = ContentValues()
                    contentValues.put("topic", cursor.getString(cursor.getColumnIndex("topic")))
                    contentValues.put("year", cursor.getInt(cursor.getColumnIndex("year")))
                    contentValues.put("name", cursor.getString(cursor.getColumnIndex("name")))
                    contentValues.put("url", cursor.getString(cursor.getColumnIndex("url")))
                    contentValues.put(
                        "startDate",
                        cursor.getInt(cursor.getColumnIndex("startDate"))
                    )
                    contentValues.put("endDate", cursor.getInt(cursor.getColumnIndex("endDate")))
                    contentValues.put("city", cursor.getStringOrNull(cursor.getColumnIndex("city")))
                    contentValues.put(
                        "country",
                        cursor.getStringOrNull(cursor.getColumnIndex("country"))
                    )
                    contentValues.put(
                        "cfpUrl",
                        cursor.getStringOrNull(cursor.getColumnIndex("cfpUrl"))
                    )
                    contentValues.put(
                        "cfpEndDate",
                        cursor.getIntOrNull(cursor.getColumnIndex("cfpEndDate"))
                    )
                    contentValues.put(
                        "twitter",
                        cursor.getStringOrNull(cursor.getColumnIndex("twitter"))
                    )
                    database.execSQL("DROP TABLE IF EXISTS `events`")
                    createEventsTable(database)
                    database.insert("events", 0, contentValues)
                } else {
                    database.execSQL("DROP TABLE IF EXISTS `events`")
                    createEventsTable(database)
                }
            }
        } catch (sqle: SQLiteException) {
            FirebaseCrashlytics.getInstance().recordException(sqle)
            FirebaseCrashlytics.getInstance().log("Failed to Migrate events from 1 to 2")
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            FirebaseCrashlytics.getInstance().log("Failed to Migrate events from 1 to 2")
        }
    }

    private fun createEventsTable(database: SupportSQLiteDatabase) {
        database.execSQL(
            """"CREATE TABLE IF NOT EXISTS `events` (
                `topic` TEXT NOT NULL,
                `year` INTEGER NOT NULL,
                `name` TEXT NOT NULL,
                `url` TEXT NOT NULL,
                `startDate` INTEGER NOT NULL,
                `endDate` INTEGER NOT NULL,
                `city` TEXT,
                `country` TEXT,
                `cfpUrl` TEXT,
                `cfpEndDate` INTEGER,
                `twitter` TEXT,
                `online` INTEGER NOT NULL DEFAULT 0,
                 PRIMARY KEY(`name`, `topic`, `startDate`))""".trimIndent()
        )
    }

    private fun migrateFrom1To2BookmarkedEventsTable(database: SupportSQLiteDatabase) {
        try {
            val cursor = database.query("SELECT * from bookmarked_events")
            cursor.use {
                if (it.moveToFirst()) {
                    val contentValues = ContentValues()
                    contentValues.put(
                        "cfp_reminder_enabled",
                        cursor.getInt(cursor.getColumnIndex("cfp_reminder_enabled"))
                    )
                    contentValues.put(
                        "cfp_reminder_req_id",
                        cursor.getIntOrNull(cursor.getColumnIndex("cfp_reminder_req_id"))
                    )
                    contentValues.put(
                        "cfp_reminder_time",
                        cursor.getIntOrNull(cursor.getColumnIndex("cfp_reminder_time"))
                    )
                    contentValues.put("topic", cursor.getString(cursor.getColumnIndex("topic")))
                    contentValues.put("year", cursor.getInt(cursor.getColumnIndex("year")))
                    contentValues.put("name", cursor.getString(cursor.getColumnIndex("name")))
                    contentValues.put("url", cursor.getString(cursor.getColumnIndex("url")))
                    contentValues.put(
                        "startDate",
                        cursor.getInt(cursor.getColumnIndex("startDate"))
                    )
                    contentValues.put("endDate", cursor.getInt(cursor.getColumnIndex("endDate")))
                    contentValues.put("city", cursor.getStringOrNull(cursor.getColumnIndex("city")))
                    contentValues.put(
                        "country",
                        cursor.getStringOrNull(cursor.getColumnIndex("country"))
                    )
                    contentValues.put(
                        "cfpUrl",
                        cursor.getStringOrNull(cursor.getColumnIndex("cfpUrl"))
                    )
                    contentValues.put(
                        "cfpEndDate",
                        cursor.getIntOrNull(cursor.getColumnIndex("cfpEndDate"))
                    )
                    contentValues.put(
                        "twitter",
                        cursor.getStringOrNull(cursor.getColumnIndex("twitter"))
                    )
                    database.execSQL("DROP TABLE IF EXISTS `bookmarked_events`")
                    createBookmarkedEventsTable(database)
                    database.insert("bookmarked_events", 0, contentValues)
                } else {
                    database.execSQL("DROP TABLE IF EXISTS `bookmarked_events`")
                    createBookmarkedEventsTable(database)
                }
            }
        } catch (sqle: SQLiteException) {
            FirebaseCrashlytics.getInstance().recordException(sqle)
            FirebaseCrashlytics.getInstance().log("Failed to Migrate bookmarked_events from 1 to 2")
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            FirebaseCrashlytics.getInstance()
                .log("Failed to Migrate bookmarked_events  from 1 to 2")
        }
    }

    private fun createBookmarkedEventsTable(database: SupportSQLiteDatabase) {
        database.execSQL(
            """CREATE TABLE IF NOT EXISTS `bookmarked_events` (
               `cfp_reminder_enabled` INTEGER NOT NULL,
               `cfp_reminder_req_id` INTEGER,
               `cfp_reminder_time` INTEGER,
               `topic` TEXT NOT NULL,
               `year` INTEGER NOT NULL,
               `name` TEXT NOT NULL,
               `url` TEXT NOT NULL,
               `startDate` INTEGER NOT NULL,
               `endDate` INTEGER NOT NULL,
               `city` TEXT NOT NULL,
               `country` TEXT NOT NULL,
               `cfpUrl` TEXT,
               `cfpEndDate` INTEGER,
               `twitter` TEXT,
               `online` INTEGER NOT NULL DEFAULT 0,
                PRIMARY KEY(`name`, `topic`, `startDate`))""".trimIndent()
        )
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(): NetworkDBHelper = NetworkDBHelperImpl(confApplication)

    @Provides
    @Singleton
    fun provideWorkManagerConfiguration(managerWorkerFactory: ManagerWorkerFactory): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(managerWorkerFactory)
            .build()

    @Provides
    @Singleton
    fun providesSharedPreferences(): SharedPreferences = confApplication.getSharedPreferences(
        "UserPrefs",
        Context.MODE_PRIVATE
    )

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Singleton
    @Provides
    fun provideScreenResourceHelper(themePreferences: ThemePreferences): ScreenResourcesHelper =
        ScreenUtils(themePreferences)
}
