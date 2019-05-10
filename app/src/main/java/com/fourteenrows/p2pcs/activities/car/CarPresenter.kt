package com.fourteenrows.p2pcs.activities.car

import android.app.Activity
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.car.CarContractor.*
import com.fourteenrows.p2pcs.model.FetchedVehicle
import com.fourteenrows.p2pcs.model.ModelFirebase
import kotlinx.android.synthetic.main.activity_car.*

class CarPresenter(toView: View) : Presenter, CompleteListener {

    private var view = toView
    private var interactor: Interactor = CarInteractor(this)

    init {
        view.initView()
        loadVehicles()
    }

    override fun loadVehicles() {
        interactor.fetchVehicles()
    }


    override fun editVehicle(car: FetchedVehicle) {

        view.editVehicle(car)
    }

    override fun deleteCar(carId: String) {
        ModelFirebase.delete("Cars", carId)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    view.makeAlertDialog(R.string.vehicle_delete_success, R.string.success)
                } else {
                    view.makeAlertDialog(R.string.vehicle_delete_error_db, R.string.error)
                }
                loadVehicles()
            }
    }

    override fun checkReservations(carId: String) {
        ModelFirebase.getCarReservations(carId)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result!!.size() == 0) {
                        view.makeConfirmationDialog(carId)
                    } else {
                        view.makeAlertDialog(R.string.vehicle_delete_error_reservations, R.string.error)
                    }
                }
            }
    }

    override fun onSuccess(vehicles: ArrayList<FetchedVehicle>) {
        if (vehicles.size != 0) {
            view.setRecyclerAdapter(CarRecycleAdapter(vehicles, this))
        } else {
            (view as Activity).message.visibility = android.view.View.VISIBLE
            (view as Activity).recycleView.visibility = android.view.View.GONE
        }
    }

    override fun onFailure() {
        //TODO
    }
}