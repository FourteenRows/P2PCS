package com.fourteenrows.p2pcs.activities.car.add_car

import com.fourteenrows.p2pcs.generalview.IView
import com.fourteenrows.p2pcs.model.Vehicle
import java.util.*

interface AddCarContractor {

    interface View : IView {
        fun changeView(message: Int, title: Int)
        fun makeAlertDialog(message: Int, title: Int)
        fun makeCalendarDialog()
    }

    interface Presenter {
        fun checkVehicleValues(plate: String, model: String, seats: String, endDate: String)
        fun formatDate(date: Date): String
        fun nextNDays(days: Int): Long
    }

    interface Interactor {
        fun checkNumberOfSeats(seats: Long): Boolean
        fun checkPlateAlreadyExists(car: Vehicle)
        fun checkValueIsEmpty(value: String): Boolean
        fun formatDate(date: Date): String
        fun checkValueIsPlate(value: String): Boolean
        fun getUid(): String
        fun insertVehicle(car: Vehicle)
        fun nextNDays(days: Int): Long
        fun truncateDateToDay(date: Date): Date
    }

    interface CompleteListener {
        fun onSuccess(car: Vehicle)
        fun onFailure()
    }
}