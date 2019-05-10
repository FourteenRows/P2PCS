package com.fourteenrows.p2pcs.model

open class ActiveReservationObject(
    override val cardType: ReservationCardType,
    open val vehicleModel: String,
    open val endDate: String,
    open val hours: String,
    open val rid: String
) : CardObject(cardType)