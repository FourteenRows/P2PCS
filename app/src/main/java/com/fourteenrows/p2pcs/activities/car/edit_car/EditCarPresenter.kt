package com.fourteenrows.p2pcs.activities.car.edit_car

import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.car.edit_car.EditCarContractor.*
import com.fourteenrows.p2pcs.model.FetchedVehicle
import java.util.*

class EditCarPresenter(toView: View) : Presenter, CompleteListener {

    private var view = toView as EditCarView
    private var interactor: Interactor = EditCarInteractor(this)

    init {
        view.initView()
        fillData()
    }

    override fun fillData() {
        val instant = view.intent.getBooleanExtra("instant_availability", true)
        val toFinal = view.intent.getLongExtra("final_availability", 0)
        var final: Date? = null
        if (toFinal != 0L) {
            final = Date(toFinal)
        }
        val model = view.intent.getStringExtra("model")
        val plate = view.intent.getStringExtra("plate")
        val seats = view.intent.getLongExtra("seats", 1)
        view.adapt(final, instant, model, plate, seats)
    }

    override fun formatDate(date: Date): String = interactor.formatDate(date)

    override fun checkVehicleValues(plate: String, model: String, seats: String, endDate: String, nowDate: Boolean) {
        if (!interactor.checkValueIsEmpty(plate) || !interactor.checkValueIsEmpty(model)
            || !interactor.checkValueIsEmpty(seats)
        ) {
            view.makeAlertDialog(R.string.all_fields_required, R.string.error)
            return
        }

        if (!interactor.checkNumberOfSeats(seats.toLong())) {
            view.makeAlertDialog(R.string.number_of_seats, R.string.error)
            return
        }

        if (plate != view.intent.getStringExtra("plate")) {
            if (!interactor.checkValueIsPlate(plate)) {
                view.makeAlertDialog(R.string.plate_not_plate, R.string.error)
                return
            }
        }

        val date =
            if (interactor.checkValueIsEmpty(endDate)) interactor.truncateDateToDay(Date(endDate.toLong())) else Date(
                32500915200000
            )
        val owner = view.intent.getStringExtra("owner")
        val carId = view.intent.getStringExtra("carId")
        val car = FetchedVehicle(date, model, owner, plate, seats.toLong(), carId, nowDate)
        if (plate != view.intent.getStringExtra("plate")) {
            interactor.checkPlateAlreadyExists(car)
        } else {
            onSuccess(car)
        }
    }

    override fun nextNDays(days: Int): Long = interactor.nextNDays(days)

    override fun onFailure() {
        view.makeAlertDialog(R.string.plate_already_in, R.string.error)
        return
    }

    override fun onSuccess(car: FetchedVehicle) {
        interactor.editVehicle(car)
        view.intent.putExtra("plate", car.plate)
        view.changeView(R.string.vehicle_edit_success, R.string.success)
    }
}
