package io.rot.labs.projectconf.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import io.rot.labs.projectconf.data.model.Event


@Entity(
    tableName = "events",
    primaryKeys = ["name", "startDate"]
)
data class EventEntity(

    @Embedded
    val event: Event,

    @ColumnInfo(name = "topic")
    val topic: String

)
