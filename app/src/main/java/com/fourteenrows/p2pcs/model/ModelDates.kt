package com.fourteenrows.p2pcs.model

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.*

object ModelDates {
    //Returns XX:00 - YY:00
    fun getSlotString(start: Timestamp): String {
        var res = ""
        val dateFormat = "HH:mm"
        val toLocale = SimpleDateFormat(dateFormat, Locale.ITALIAN)
        res += toLocale.format(start.toDate())
        res += " - "
        res += toLocale.format(Date.from(start.toDate().toInstant().plus(6, ChronoUnit.HOURS)))
        return res
    }

    fun isInThePast(timestamp: Timestamp): Boolean {
        return timestamp.toDate().toInstant().plus(6, ChronoUnit.HOURS) < Timestamp.now().toDate().toInstant()
    }

    fun toLocaleTimeFormat(date: Date): String {
        val dateFormat = "dd MMMM yyyy"
        val toLocale = SimpleDateFormat(dateFormat, Locale.ITALIAN)
        return toLocale.format(date)
    }

    fun nextDayOf(date: Long, timeslot: String): Date {
        return Date.from(
            Date(date).toInstant()
                .plus(
                    timeslot.substring(0, 2).toLong(),
                    ChronoUnit.HOURS
                )
        )
    }

    fun nextNDays(days: Int): Long {
        return Date.from(truncateDateToDay(Date()).toInstant().plus(days.toLong(), ChronoUnit.DAYS)).time
    }

    fun setSlotToDate(date: Date, slot: String): Date {
        return Date.from(
            date.toInstant().truncatedTo(ChronoUnit.DAYS)
                .plus(slot.substring(0, 2).toLong(), ChronoUnit.HOURS)
        )
    }

    fun truncateDateToDay(date: Date): Date {
        return Date.from(date.toInstant().truncatedTo(ChronoUnit.DAYS))
    }
}