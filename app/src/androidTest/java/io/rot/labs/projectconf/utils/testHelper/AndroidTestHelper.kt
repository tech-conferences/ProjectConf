/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.utils.testHelper

import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.Event
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import java.util.Date

object AndroidTestHelper {

    const val milliSecondsIn1Day: Long = 86400000

    private val baseTime = System.currentTimeMillis()
    val eventDatePast = baseTime - 2 * milliSecondsIn1Day
    val eventDateFuture = baseTime + 10 * milliSecondsIn1Day
    val eventDateFuture1 = baseTime + 11 * milliSecondsIn1Day

    val cfpDateFuture = baseTime + 5 * milliSecondsIn1Day
    val cfpDateFuture1: Long = baseTime + 62 * milliSecondsIn1Day
    val cfpDatePast = baseTime - 5 * milliSecondsIn1Day

    val fakeEventEntityList = listOf(
        EventEntity(
            Event(
                "PragmaConf",
                "https://pragmaconf.tech",
                Date(eventDatePast),
                Date(),
                "Allahabad",
                "India"
            ),
            "ux",
            TimeDateUtils.getYearForDate(Date(eventDatePast))
        ), EventEntity(
            Event(
                "KotlinersConf",
                "https://kotliners.tech",
                Date(eventDatePast),
                Date(),
                "Austin",
                "U.S.A.",
                null,
                null,
                "@kotliners"
            ),
            "kotlin",
            TimeDateUtils.getYearForDate(Date(eventDatePast))
        ),
        EventEntity(
            Event(
                "Kubecon",
                "https://kubecon.tech",
                Date(eventDatePast),
                Date(),
                "San diego",
                "U.S.A.",
                "https://papercall.io/kubecon",
                Date(cfpDatePast),
                "@kubecon"
            ),
            "devops",
            TimeDateUtils.getYearForDate(Date(eventDatePast))
        ),
        EventEntity(
            Event(
                "Cloudnative",
                "https://cloudnative.tech",
                Date(eventDatePast),
                Date(),
                "San diego",
                "U.S.A.",
                "https://papercall.io/cloudnative",
                null,
                "@cloudnative"
            ),
            "devops",
            TimeDateUtils.getYearForDate(Date(eventDatePast))
        ),
        EventEntity(
            Event(
                "Rustlang",
                "https://rustlang.tech",
                Date(eventDateFuture),
                Date(eventDateFuture1),
                "San diego",
                "U.S.A.",
                null,
                Date(cfpDateFuture),
                "@rustlang"
            ),
            "rust",
            TimeDateUtils.getYearForDate(Date(eventDateFuture))
        ),
        EventEntity(
            Event(
                "Kubecon EU",
                "https://kubecon.tech",
                Date(eventDateFuture),
                Date(eventDateFuture1),
                "San diego",
                "U.S.A.",
                "https://papercall.io/kubeconEU",
                Date(cfpDateFuture1),
                "@kubeconEU"
            ),
            "devops",
            TimeDateUtils.getYearForDate(Date(eventDatePast))
        )
    )
}
