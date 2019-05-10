package com.fourteenrows.p2pcs.unitTests

import com.fourteenrows.p2pcs.model.ModelUtils
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.Date

class Utils {

    @Test
    fun getTimeSlots() {
        val right = arrayOf(
            "00:00 - 06:00",
            "06:00 - 12:00",
            "12:00 - 18:00",
            "18:00 - 00:00"
        )
        val result = ModelUtils.getTimeSlots()
        assertEquals(right.size, result.size)
        for (i in 0 until result.size)
            assertEquals(result[i], right[i])
    }

    @Test
    fun fixDate() {
        assertEquals(ModelUtils.fixDate("1234124123"), 1209600000)
        assertEquals(ModelUtils.fixDate("0"), 0)
    }

    @Test
    fun formatDate() {
        assertEquals(ModelUtils.formatDate(Date(1234556732L)), "15 gennaio 1970")
        assertEquals(ModelUtils.formatDate(Date(-1234556732L)), "17 dicembre 1969")
        assertEquals(ModelUtils.formatDate(Date(0)), "01 gennaio 1970")
    }

}