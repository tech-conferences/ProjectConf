package io.rot.labs.projectconf.utils.testHelper

import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.Event
import java.util.Date

object AndroidTestHelper {

    private const val milliSecondsIn1Day = 86400000

    val fakeEventEntityList = listOf(
        EventEntity(
            Event(
                "Pragma Conf",
                "https://pragmaconf.tech",
                Date(System.currentTimeMillis() - milliSecondsIn1Day),
                Date(),
                "Allahabad",
                "India"
            ),
            "ux"
        ), EventEntity(
            Event(
                "Kotliners Conf",
                "https://kotliners.tech",
                Date(System.currentTimeMillis() - 2 * milliSecondsIn1Day),
                Date(),
                "Austin",
                "U.S.A."
            ),
            "kotlin"
        )
    )
}
