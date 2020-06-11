package io.rot.labs.projectconf.data.model

enum class EVENT_ITEM_TYPE {
    HEADER, DETAIL
}

abstract class EventItem(
    type: EVENT_ITEM_TYPE
)
