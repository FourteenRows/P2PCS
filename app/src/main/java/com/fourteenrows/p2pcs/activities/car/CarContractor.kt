package com.fourteenrows.p2pcs.activities.car

import com.fourteenrows.p2pcs.generalview.IView
import com.fourteenrows.p2pcs.model.FetchedVehicle

interface CarContractor {

    interface View : IView {
        fun editVehicle(car: FetchedVehicle)
        fun makeAlertDialog(message: Int, title: Int)
        fun makeConfirmationDialog(value: String)
        fun setRecyclerAdapter(adapter: CarRecycleAdapter)
    }

    interface Presenter {
        fun deleteCar(carId: String)
        fun loadVehicles()
    }

    interface Interactor {
        fun fetchVehicles()
    }

    interface CompleteListener {
        fun checkReservations(carId: String)
        fun editVehicle(car: FetchedVehicle)
        fun onSuccess(vehicles: ArrayList<FetchedVehicle>)
        fun onFailure()
    }
}