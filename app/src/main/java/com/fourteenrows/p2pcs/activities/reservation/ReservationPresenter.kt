package com.fourteenrows.p2pcs.activities.reservation

import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.reservation.ReservationContractor.*
import com.fourteenrows.p2pcs.model.CardObject
import com.fourteenrows.p2pcs.model.ModelFirebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class ReservationPresenter(toView: View) : Presenter, CompleteListener {
    private var view = toView
    private val interactor = ReservationInteractor(this)

    init {
        view.initView()
    }

    override fun verifyUserIsLoggedIn(): Boolean = FirebaseAuth.getInstance().currentUser != null

    override fun loadReservations() {
        interactor.fetchReservations()
    }

    fun deleteReservation(rid: String) {
        ModelFirebase.delete("Dates", rid)
            .addOnSuccessListener {
                loadReservations()
            }
    }

    override fun confirmDeletion(rid: String) {
        interactor.getReservation(rid)
            .addOnSuccessListener {
                val map = it.data
                val asd = map?.get("start_slot") as Timestamp
                val a = asd.toDate().time + 1000L * 60L * 60L * 6L + 1000L * 60L * 60L * 2L
                if (a - Date().time > 1000L * 60L * 60L * 6L) {
                    view.makeConfirmationDialog(rid)
                } else {
                    view.makeAlertDialog(R.string.ongoing_reservation, R.string.error)
                }
            }
    }

    override fun onSuccess(reservation: ArrayList<CardObject>) {
        view.setRecyclerAdapter(ReservationRecyclerAdapter(reservation, this))
    }

    override fun onFailure() {
    }
}