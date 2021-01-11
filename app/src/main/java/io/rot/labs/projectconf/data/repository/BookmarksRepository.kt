/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.data.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.rot.labs.projectconf.data.local.db.ConfDatabase
import io.rot.labs.projectconf.data.local.db.entity.BookmarkedEvent
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BookmarksRepository @Inject constructor(database: ConfDatabase) {

    private val bookmarksDao = database.bookmarksDao()

    open fun insertBookmarkEvent(bookmarkedEvent: BookmarkedEvent): Completable {
        return bookmarksDao.insert(bookmarkedEvent)
    }

    open fun updateBookmarkEvent(bookmarkedEvent: BookmarkedEvent): Completable {
        return bookmarksDao.update(bookmarkedEvent)
    }

    open fun removeBookmarkEvent(bookmarkedEvent: BookmarkedEvent): Completable {
        return bookmarksDao.remove(bookmarkedEvent)
    }

    open fun getAllBookmarkedEvents(): Single<List<BookmarkedEvent>> {
        return bookmarksDao.getAllBookmarkedEvents()
    }

    open fun getCFPReminderEvents(): Single<List<BookmarkedEvent>> {
        return bookmarksDao.getCFPReminderEvents()
    }

    open fun getBookmarkedEvent(
        name: String,
        startDate: Date,
        topic: String
    ): Maybe<BookmarkedEvent> {
        return bookmarksDao.getBookmarkedEvent(name, startDate, topic)
    }
}
