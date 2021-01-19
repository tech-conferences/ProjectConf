/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.rot.labs.projectconf.data.local.db.dao.BookmarksDao
import io.rot.labs.projectconf.data.local.db.dao.EventsDao
import io.rot.labs.projectconf.data.local.db.entity.BookmarkedEvent
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import javax.inject.Singleton

@Singleton
@Database(
    entities = [EventEntity::class, BookmarkedEvent::class],
    exportSchema = false,
    version = 2
)
@TypeConverters(DateConverters::class)
abstract class ConfDatabase : RoomDatabase() {

    abstract fun eventsDao(): EventsDao

    abstract fun bookmarksDao(): BookmarksDao
}
