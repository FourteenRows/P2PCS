package com.fourteenrows.p2pcs.activities.reservation.add_reservation

import com.fourteenrows.p2pcs.generalview.IView

interface AddReservationContractor {
    interface View : IView {
        fun changeView(message: Int, title: Int)
        fun chooseVehicle(date: String, zone: String)
        fun makeAlertDialog(message: Int, title: Int)
        fun makeCalendarDialog()
        fun makeRadioDialog()
        fun refresh()
        fun refillContent(carId: String, date: Long, zone: String)
    }

    interface Presenter {
        fun checkValues(date: String, zone: String)
        fun insertReservation()
    }

    interface Interactor {
        fun checkValueIsEmpty(value: String): Boolean
        fun insertReservation(carId: String, date: Long, model: String, zone: String)
    }

    interface CompleteListener {
        fun onSuccess()
    }
}