package com.fourteenrows.p2pcs.model

data class PastReservationObject(
    override val cardType: ReservationCardType,
    override val vehicleModel: String,
    override val endDate: String,
    override val hours: String,
    override val rid: String,
    val totalCost: Double
) : ActiveReservationObject(cardType, vehicleModel, endDate, hours, rid)