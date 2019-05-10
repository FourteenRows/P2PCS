package com.fourteenrows.p2pcs.unitTests

import com.fourteenrows.p2pcs.model.ModelDates
import com.google.firebase.Timestamp
import org.junit.Assert.*
import org.junit.Test
import java.time.temporal.ChronoUnit
import java.util.Date

class Date {

    @Test
    fun isInThePast() {
        assertTrue(ModelDates.isInThePast(Timestamp(0, 0)))
        assertFalse(ModelDates.isInThePast(Timestamp(10000000000, 0)))
    }

    @Test
    fun toLocaleTimeFormat() {
        assertEquals(ModelDates.toLocaleTimeFormat(Date(0)), "01 gennaio 1970")
        val date = Date(1231231231L)
        assertEquals(ModelDates.toLocaleTimeFormat(date), "15 gennaio 1970")
    }

    @Test
    fun nextDayOf() {
        assertEquals(ModelDates.nextDayOf(1234567, "06 - 06"), Date(22834567))
        assertEquals(ModelDates.nextDayOf(1234567, "12"), Date(44434567))
        assertEquals(ModelDates.nextDayOf(1234567, "00AAAA"), Date(1234567))
    }

    @Test
    fun nextNDays() {
        assertEquals(
            ModelDates.truncateDateToDay(
                Date.from(
                    Date().toInstant()
                        .plus(3L, ChronoUnit.DAYS)
                )
            ).time, ModelDates.nextNDays(3)
        )
        assertEquals(
            ModelDates.truncateDateToDay(
                Date.from(
                    Date().toInstant()
                        .plus(0L, ChronoUnit.DAYS)
                )
            ).time, ModelDates.nextNDays(0)
        )
    }

    @Test
    fun TruncaDateAt() {
        assertEquals(ModelDates.truncateDateToDay(Date(555555555L)), Date(518400000))
        assertEquals(ModelDates.truncateDateToDay(Date(0)), Date(0))
    }

}