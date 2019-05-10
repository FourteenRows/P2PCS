package com.fourteenrows.p2pcs.unitTests

import com.fourteenrows.p2pcs.model.ModelValidator
import junit.framework.Assert.assertEquals
import org.junit.Test

class Validator {

    @Test
    fun checkNumberOfSeats() {
        assertEquals(ModelValidator.checkNumberOfSeats(1), true)
        assertEquals(ModelValidator.checkNumberOfSeats(-1), false)
        assertEquals(ModelValidator.checkNumberOfSeats(0), false)
    }

    @Test
    fun checkValueIsEmail() {
        assertEquals(ModelValidator.checkValueIsEmail("asd@asd.asd"), true)
        assertEquals(ModelValidator.checkValueIsEmail("asd@asd."), false)
        assertEquals(ModelValidator.checkValueIsEmail("asd@asd"), false)
        assertEquals(ModelValidator.checkValueIsEmail("asd@.sd"), false)
        assertEquals(ModelValidator.checkValueIsEmail("asd@"), false)
        assertEquals(ModelValidator.checkValueIsEmail("@asd.asd"), false)
        assertEquals(ModelValidator.checkValueIsEmail("@"), false)
        assertEquals(ModelValidator.checkValueIsEmail("asd.asd"), false)
        assertEquals(ModelValidator.checkValueIsEmail("as d@sd"), false)
        assertEquals(ModelValidator.checkValueIsEmail("asd@asd .asd"), false)
        assertEquals(ModelValidator.checkValueIsEmail(""), false)
    }

    @Test
    fun checkValueIsPlate() {
        assertEquals(ModelValidator.checkValueIsPlate("AA123AA"), true)
        assertEquals(ModelValidator.checkValueIsPlate("AA123A"), false)
        assertEquals(ModelValidator.checkValueIsPlate("A123AA"), false)
        assertEquals(ModelValidator.checkValueIsPlate("123"), false)
        assertEquals(ModelValidator.checkValueIsPlate("AA13AA"), false)
        assertEquals(ModelValidator.checkValueIsPlate("AA1AA"), false)
        assertEquals(ModelValidator.checkValueIsPlate("AAAA"), false)
        assertEquals(ModelValidator.checkValueIsPlate("A1AA123A"), false)
        assertEquals(ModelValidator.checkValueIsPlate("12AAA12"), false)
        assertEquals(ModelValidator.checkValueIsPlate("A 123AA"), false)
    }

    @Test
    fun checkValueIsEmpty() {
        assertEquals(ModelValidator.checkValueIsEmpty("asd"), true)
        assertEquals(ModelValidator.checkValueIsEmpty(""), false)
        assertEquals(ModelValidator.checkValueIsEmpty(" "), true)
    }

    @Test
    fun checkStringLength() {
        assertEquals(ModelValidator.checkStringLength("asd", 3), true)
        assertEquals(ModelValidator.checkStringLength("asd", 2), true)
        assertEquals(ModelValidator.checkStringLength("asd", 4), false)
        assertEquals(ModelValidator.checkStringLength("", 0), true)
    }

    @Test
    fun checkStringsEqual() {
        assertEquals(ModelValidator.checkStringsEqual("asd", "asd"), true)
        assertEquals(ModelValidator.checkStringsEqual("asd", "as"), false)
        assertEquals(ModelValidator.checkStringsEqual("sd", "asd"), false)
        assertEquals(ModelValidator.checkStringsEqual("", ""), true)
    }
}