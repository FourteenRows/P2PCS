package com.fourteenrows.p2pcs.activities.car.edit_car

import com.fourteenrows.p2pcs.generalview.IView
import com.fourteenrows.p2pcs.model.FetchedVehicle
import java.util.*

interface EditCarContractor {

    interface View : IView {
        fun adapt(final: Date?, instant: Boolean, model: String, plate: String, seats: Long)
        fun changeView(message: Int, title: Int)
        fun makeAlertDialog(message: Int, title: Int)
        fun makeCalendarDialog()
    }

    interface Presenter {
        fun checkVehicleValues(plate: String, model: String, seats: String, endDate: String, nowDate: Boolean)
        fun fillData()
        fun formatDate(date: Date): String
        fun nextNDays(days: Int): Long
    }

    interface Interactor {
        fun checkNumberOfSeats(seats: Long): Boolean
        fun checkPlateAlreadyExists(car: FetchedVehicle)
        fun checkValueIsEmpty(value: String): Boolean
        fun checkValueIsPlate(value: String): Boolean
        fun editVehicle(car: FetchedVehicle)
        fun formatDate(date: Date): String
        fun getUid(): String
        fun nextNDays(days: Int): Long
        fun truncateDateToDay(date: Date): Date
    }

    interface CompleteListener {
        fun onSuccess(car: FetchedVehicle)
        fun onFailure()
    }
}