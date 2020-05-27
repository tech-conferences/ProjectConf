package io.rot.labs.projectconf.utils.common

import android.util.Log
import java.awt.font.NumericShaper
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object TimeDateUtils {

    private val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
    }

    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    fun getFirstDayOfCurrentYear(): Date {
        val currYear = calendar.get(Calendar.YEAR)
        return sdf.parse("$currYear-01-01")!!
    }

    fun getLastDayOfCurrentYear(): Date {
        val currYear = calendar.get(Calendar.YEAR)
        return sdf.parse("$currYear-12-31")!!
    }

    fun getCurrentDate(): Date {
        val currYear = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DATE)
        val date = sdf.parse("$currYear-${month.toString().padStart(2, '0')}-$day")
        return date!!
    }

    fun getConfYearsList(): List<Int> {
        val baseYear = 2014
        val currYear = calendar.get(Calendar.YEAR)
        return IntRange(baseYear, currYear + 1).toList()
    }

}