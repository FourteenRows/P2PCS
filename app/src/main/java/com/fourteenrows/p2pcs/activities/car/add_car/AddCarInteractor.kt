package com.fourteenrows.p2pcs.activities.car.add_car

import com.fourteenrows.p2pcs.activities.car.add_car.AddCarContractor.CompleteListener
import com.fourteenrows.p2pcs.activities.car.add_car.AddCarContractor.Interactor
import com.fourteenrows.p2pcs.model.*
import java.util.*

class AddCarInteractor(private val listener: CompleteListener) : Interactor {

    override fun checkNumberOfSeats(seats: Long): Boolean = ModelValidator.checkNumberOfSeats(seats)

    override fun checkPlateAlreadyExists(car: Vehicle) {
        ModelFirebase.checkNewPlate(car.plate)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result!!.documents.size > 0) {
                        listener.onFailure()
                    } else {
                        listener.onSuccess(car)
                    }
                }
            }
    }

    override fun checkValueIsEmpty(value: String): Boolean {
        return ModelValidator.checkValueIsEmpty(value)
    }

    override fun checkValueIsPlate(value: String): Boolean = ModelValidator.checkValueIsPlate(value)

    override fun formatDate(date: Date): String = ModelUtils.formatDate(date)

    override fun getUid(): String = ModelFirebase.getUid()!!

    override fun insertVehicle(car: Vehicle) {
        ModelFirebase.insertVehicle(car)
    }

    override fun nextNDays(days: Int): Long = ModelDates.nextNDays(days)

    override fun truncateDateToDay(date: Date): Date = ModelDates.truncateDateToDay(date)
}