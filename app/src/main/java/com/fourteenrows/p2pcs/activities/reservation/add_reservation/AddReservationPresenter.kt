package com.fourteenrows.p2pcs.activities.reservation.add_reservation

import android.app.Activity
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.reservation.add_reservation.AddReservationContractor.*

class AddReservationPresenter(toView: View) : Presenter, CompleteListener {
    private var view = toView
    private val interactor = AddReservationInteractor(this)

    init {
        view.initView()
        val viewAct = view as Activity
        val model = viewAct.intent.getStringExtra("model")
        if (model != null) {
            val date = viewAct.intent.getStringExtra("date").toLong()
            val zone = viewAct.intent.getStringExtra("zone").toString()
            view.refillContent(model, date, zone)
        }

    }

    override fun checkValues(date: String, zone: String) {
        if (!interactor.checkValueIsEmpty(date) || !interactor.checkValueIsEmpty(zone)) {
            view.makeAlertDialog(R.string.reservation_fill, R.string.error)
            return
        }
        view.chooseVehicle(date, zone)
    }

    override fun insertReservation() {
        val viewAct = view as Activity
        val model = viewAct.intent.getStringExtra("model")
        if (model == null) {
            view.makeAlertDialog(R.string.all_fields_required, R.string.error)
        } else {
            val carId = viewAct.intent.getStringExtra("carId")
            val date = viewAct.intent.getStringExtra("date").toLong()
            val zone = viewAct.intent.getStringExtra("zone").toString()
            interactor.insertReservation(carId, date, model, zone)
        }
    }

    override fun onSuccess() {
        view.changeView(R.string.reservation_successful, R.string.success)
    }
}