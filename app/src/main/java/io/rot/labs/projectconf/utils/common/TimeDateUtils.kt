package io.rot.labs.projectconf.utils.common

import java.text.SimpleDateFormat
import java.util.*

object TimeDateUtils {

    private val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
    }

    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    fun getFirstDayOfYear(year: Int): Date {
        return sdf.parse("$year-01-01")!!
    }

    fun getLastDayOfYear(year: Int): Date {
        return sdf.parse("$year-12-31")!!
    }

    fun getCurrentDate(): Date {
        val currYear = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DATE)
        val date = sdf.parse("$currYear-${month.toString().padStart(2, '0')}-$day")
        return date!!
    }


    fun getLastDateOfCurrentMonth(): Date {
        val currYear = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.getActualMaximum(Calendar.DATE)
        val date = sdf.parse("$currYear-${month.toString().padStart(2, '0')}-$day")
        return date!!
    }

    fun getConfYearsList(): List<Int> {
        val baseYear = 2014
        val currYear = calendar.get(Calendar.YEAR)
        return IntRange(baseYear, currYear + 1).toList()
    }

    fun getFormattedDay(date: Date): String {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = date.time
        }
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy")
        return sdf.format(calendar.time)

    }

    fun getEventPeriod(date: Date): String {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = date.time
        }
        val sdf = SimpleDateFormat("MMMM, yyyy")
        return sdf.format(calendar.time)
    }

}