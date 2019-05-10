package com.fourteenrows.p2pcs.activities.reservation.add_reservation.add_reservation_vehicle

import android.app.Activity
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.fourteenrows.p2pcs.activities.reservation.add_reservation.AddReservationView
import com.fourteenrows.p2pcs.activities.reservation.add_reservation.add_reservation_vehicle.AddReservationVehicleContractor.*
import com.fourteenrows.p2pcs.model.ModelFirebase
import com.fourteenrows.p2pcs.model.ModelUtils
import kotlinx.android.synthetic.main.activity_add_reservation_vehicle.*
import java.util.*

class AddReservationVehiclePresenter(toView: View) : Presenter, CompleteListener {
    private var view = toView as AddReservationVehicleView
    private val interactor = AddReservationVehicleInteractor(this)

    init {
        view.initView()
        val date = view.intent.getStringExtra("date")
        val zone = view.intent.getStringExtra("zone").toString()

        loadVehicle(ModelUtils.fixDate(date), zone)
    }

    override fun loadVehicle(date: Long, timeSlot: String) {
        interactor.fetchVehicles(date, timeSlot)
    }

    override fun updateData(field: String, input: String) {
        if (!interactor.checkValueIsEmpty(input)) {
            view.makeAlertDialog("Compila il campo", "Errore")
            return
        }
        ModelFirebase.updateField(field, input)
        view.refresh()
    }


    override fun onSuccess(reservation: ArrayList<VehicleObject>) {
        if (reservation.size != 0) {
            view.setRecyclerAdapter(AddReservationVehicleRecyclerAdapter(reservation, this))
        } else {
            (view as Activity).message.visibility = android.view.View.VISIBLE
            (view as Activity).recycleView.visibility = android.view.View.GONE
        }
    }

    override fun onFailure() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun selectVehicle(cid: String, model: String) {
        val intent = Intent(view, AddReservationView::class.java)
        val date = view.intent.getStringExtra("date")
        val zone = view.intent.getStringExtra("zone").toString()
        intent.putExtra("model", model)
        intent.putExtra("carId", cid)
        intent.putExtra("date", date)
        intent.putExtra("zone", zone)
        startActivity(view, intent, null)
    }
}