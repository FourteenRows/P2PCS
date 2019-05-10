package com.fourteenrows.p2pcs.activities.reservation.add_reservation.add_reservation_vehicle

import com.fourteenrows.p2pcs.generalview.IView

interface AddReservationVehicleContractor {
    interface View : IView {
        fun makeAlertDialog(message: String, title: String)
        fun refresh()
        fun setRecyclerAdapter(addReservationVehicleRecyclerAdapter: AddReservationVehicleRecyclerAdapter)
    }

    interface Presenter {
        fun updateData(field: String, input: String)
        fun loadVehicle(date: Long, timeSlot: String)
    }

    interface Interactor {
        fun checkValueIsEmpty(input: String): Boolean
        fun fetchVehicles(date: Long, timeSlot: String)
    }

    interface CompleteListener {
        fun onSuccess(reservation: ArrayList<VehicleObject>)
        fun onFailure()
        fun selectVehicle(cid: String, model: String)
    }
}