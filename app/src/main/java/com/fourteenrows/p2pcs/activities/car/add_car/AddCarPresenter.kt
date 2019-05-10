package com.fourteenrows.p2pcs.activities.car.add_car

import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.car.add_car.AddCarContractor.*
import com.fourteenrows.p2pcs.model.Vehicle
import java.util.*

class AddCarPresenter(toView: View) : Presenter, CompleteListener {

    private var view = toView
    private var interactor: Interactor = AddCarInteractor(this)

    init {
        view.initView()
    }

    override fun checkVehicleValues(plate: String, model: String, seats: String, endDate: String) {
        if (!interactor.checkValueIsEmpty(plate) || !interactor.checkValueIsEmpty(model)
            || !interactor.checkValueIsEmpty(seats)
        ) {
            view.makeAlertDialog(R.string.all_fields_required, R.string.error)
            return
        }

        if (!interactor.checkValueIsPlate(plate)) {
            view.makeAlertDialog(R.string.plate_not_plate, R.string.error)
            return
        }

        if (!interactor.checkNumberOfSeats(seats.toLong())) {
            view.makeAlertDialog(R.string.number_of_seats, R.string.error)
            return
        }

        val date =
            if (interactor.checkValueIsEmpty(endDate)) interactor.truncateDateToDay(Date(endDate.toLong())) else Date(
                32500915200000
            )
        val car = Vehicle(date, model, interactor.getUid(), plate, seats.toLong(), true)
        interactor.checkPlateAlreadyExists(car)
    }

    override fun formatDate(date: Date): String = interactor.formatDate(date)

    override fun nextNDays(days: Int): Long = interactor.nextNDays(days)

    override fun onFailure() {
        view.makeAlertDialog(R.string.plate_already_in, R.string.error)
        return
    }

    override fun onSuccess(car: Vehicle) {
        interactor.insertVehicle(car)
        view.changeView(R.string.vehicle_insert, R.string.success)
    }
}