package com.fourteenrows.p2pcs.model

import java.util.*

open class Vehicle(
    open val final_availability: Date?,
    open val model: String,
    open val owner: String,
    open val plate: String,
    open val seats: Long,
    open val instant_availability: Boolean
)