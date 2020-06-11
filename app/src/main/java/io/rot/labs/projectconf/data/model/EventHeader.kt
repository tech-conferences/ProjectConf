package io.rot.labs.projectconf.data.model

data class EventHeader(
    val period: String
) : EventItem(EVENT_ITEM_TYPE.HEADER)
