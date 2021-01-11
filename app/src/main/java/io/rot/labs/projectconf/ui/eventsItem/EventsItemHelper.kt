/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.eventsItem

import android.content.Context
import android.content.Intent
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.EventHeader
import io.rot.labs.projectconf.data.model.EventItem
import io.rot.labs.projectconf.ui.eventsList.EventsListActivity
import io.rot.labs.projectconf.utils.common.TimeDateUtils

object EventsItemHelper {

    @JvmStatic
    fun transformToInterleavedList(list: List<EventEntity>): List<EventItem> {
        val periodEventListMap = mutableMapOf<String, MutableList<EventEntity>>()

        list.forEach {
            val eventPeriod = TimeDateUtils.getEventPeriod(it.event.startDate)
            var eventList = periodEventListMap[eventPeriod]
            if (eventList == null) {
                eventList = mutableListOf()
                eventList.add(it)
                periodEventListMap[eventPeriod] = eventList
            } else {
                eventList.add(it)
            }
        }

        val result = mutableListOf<EventItem>()

        periodEventListMap.forEach {
            result.add(EventHeader(it.key))
            it.value.forEach { event ->
                result.add(event)
            }
        }

        return result
    }

    @JvmStatic
    fun navigateToEventsListActivity(
        context: Context,
        topicTitle: String,
        topicSub: String,
        list: ArrayList<String>,
        isArchive: Boolean = false,
        archiveYear: Int = -1
    ) {
        context.startActivity(Intent(context, EventsListActivity::class.java).apply {
            putExtra(
                EventsListActivity.TOPIC_TITLE,
                topicTitle
            )
            putExtra(
                EventsListActivity.TOPIC_SUB,
                topicSub
            )
            putExtra(
                EventsListActivity.TOPICS_LIST,
                list
            )
            putExtra(
                EventsListActivity.IS_ARCHIVE,
                isArchive
            )
            putExtra(
                EventsListActivity.ARCHIVE_YEAR,
                archiveYear
            )
        })
    }
}
