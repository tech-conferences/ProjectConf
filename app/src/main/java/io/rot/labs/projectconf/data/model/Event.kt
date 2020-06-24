package io.rot.labs.projectconf.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import java.util.Date
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(

    @Expose
    @ColumnInfo(name = "name")
    val name: String,

    @Expose
    @ColumnInfo(name = "url")
    val url: String,

    @Expose
    @ColumnInfo(name = "startDate")
    val startDate: Date,

    @Expose
    @ColumnInfo(name = "endDate")
    val endDate: Date,

    @Expose
    @ColumnInfo(name = "city")
    val city: String,

    @Expose
    @ColumnInfo(name = "country")
    val country: String,

    @Expose
    @ColumnInfo(name = "cfpUrl")
    val cfpUrl: String? = null,

    @Expose
    @ColumnInfo(name = "cfpEndDate")
    val cfpEndDate: Date? = null,

    @Expose
    @ColumnInfo(name = "twitter")
    val twitter: String? = null
) : Parcelable
