package io.rot.labs.projectconf.data.model

enum class EVENT_BASE_TYPE {
    HEADER, DETAIL
}

abstract class EventBase(
    type: EVENT_BASE_TYPE
)
