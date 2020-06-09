package io.rot.labs.projectconf.data.model

data class EventHeader(
    val period: String
) : EventBase(EVENT_BASE_TYPE.HEADER)
