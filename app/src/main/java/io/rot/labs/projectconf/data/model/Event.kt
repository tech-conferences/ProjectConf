/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Date
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(

    @Expose
    @ColumnInfo(name = "name")
    @SerializedName(value = "name")
    val name: String,

    @Expose
    @ColumnInfo(name = "url")
    @SerializedName(value = "url")
    val url: String,

    @Expose
    @ColumnInfo(name = "startDate")
    @SerializedName(value = "startDate")
    val startDate: Date,

    @Expose
    @ColumnInfo(name = "endDate")
    @SerializedName(value = "endDate")
    val endDate: Date,

    @Expose
    @ColumnInfo(name = "city")
    @SerializedName(value = "city")
    val city: String,

    @Expose
    @ColumnInfo(name = "country")
    @SerializedName(value = "country")
    val country: String,

    @Expose
    @ColumnInfo(name = "cfpUrl")
    @SerializedName(value = "cfpUrl")
    val cfpUrl: String? = null,

    @Expose
    @ColumnInfo(name = "cfpEndDate")
    @SerializedName(value = "cfpEndDate")
    val cfpEndDate: Date? = null,

    @Expose
    @ColumnInfo(name = "twitter")
    @SerializedName(value = "twitter")
    val twitter: String? = null
) : Parcelable
