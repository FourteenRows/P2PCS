package com.fourteenrows.p2pcs.model

import java.util.*

data class FetchedVehicle(
    override val final_availability: Date?,
    override val model: String,
    override val owner: String,
    override val plate: String,
    override val seats: Long,
    val cid: String,
    override val instant_availability: Boolean
) : Vehicle(final_availability, model, owner, plate, seats, instant_availability), CarMessage {
    override fun getMessage(): String {
        return "Non sono presenti veicoli"
    }
}