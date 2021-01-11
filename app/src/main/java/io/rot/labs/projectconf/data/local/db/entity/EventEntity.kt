/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.data.local.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import io.rot.labs.projectconf.data.model.EVENT_ITEM_TYPE
import io.rot.labs.projectconf.data.model.Event
import io.rot.labs.projectconf.data.model.EventItem
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "events",
    primaryKeys = ["name", "topic", "startDate"]
)
@Parcelize
data class EventEntity(

    @Embedded
    val event: Event,

    @ColumnInfo(name = "topic")
    val topic: String,

    @ColumnInfo(name = "year")
    val year: Int

) : EventItem(EVENT_ITEM_TYPE.DETAIL), Parcelable
