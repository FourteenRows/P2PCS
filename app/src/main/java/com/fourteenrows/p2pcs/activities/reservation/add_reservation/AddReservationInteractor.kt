package com.fourteenrows.p2pcs.activities.reservation.add_reservation

import com.fourteenrows.p2pcs.activities.reservation.add_reservation.AddReservationContractor.CompleteListener
import com.fourteenrows.p2pcs.activities.reservation.add_reservation.AddReservationContractor.Interactor
import com.fourteenrows.p2pcs.model.ModelDates
import com.fourteenrows.p2pcs.model.ModelFirebase
import com.fourteenrows.p2pcs.model.ModelValidator
import com.fourteenrows.p2pcs.model.Reservation
import java.util.*

class AddReservationInteractor(private val listener: CompleteListener) : Interactor {

    override fun checkValueIsEmpty(value: String): Boolean = ModelValidator.checkValueIsEmpty(value)

    override fun insertReservation(carId: String, date: Long, model: String, zone: String) {
        ModelFirebase.insertReservation(
            Reservation(carId, model, ModelFirebase.getUid()!!, ModelDates.setSlotToDate(Date(date), zone))
        )
            .addOnSuccessListener { listener.onSuccess() }
    }

}