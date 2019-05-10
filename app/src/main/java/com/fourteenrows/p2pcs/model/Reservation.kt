package com.fourteenrows.p2pcs.model

import java.util.*

class Reservation(
    val car: String,
    val model: String,
    val owner: String,
    var start_slot: Date
)