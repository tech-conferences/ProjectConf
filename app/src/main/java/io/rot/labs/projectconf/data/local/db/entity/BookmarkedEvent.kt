/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import java.io.Serializable

@Entity(
    tableName = "bookmarked_events",
    primaryKeys = ["name", "topic", "startDate"]
)
data class BookmarkedEvent(
    @Embedded
    val eventEntity: EventEntity,

    @ColumnInfo(name = "cfp_reminder_enabled")
    var cfpReminderEnabled: Boolean = false,

    @ColumnInfo(name = "cfp_reminder_req_id")
    var cfpReminderId: Int? = null,

    @ColumnInfo(name = "cfp_reminder_time")
    var cfpReminderTimeMillis: Long? = null

) : Serializable
