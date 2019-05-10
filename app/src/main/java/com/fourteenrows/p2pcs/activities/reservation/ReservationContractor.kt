package com.fourteenrows.p2pcs.activities.reservation

import com.fourteenrows.p2pcs.generalview.IView
import com.fourteenrows.p2pcs.model.CardObject
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

interface ReservationContractor {
    interface View : IView {
        fun makeAlertDialog(message: Int, title: Int)
        fun makeConfirmationDialog(rid: String)
        fun resetView()
        fun setRecyclerAdapter(adapter: ReservationRecyclerAdapter)
    }

    interface Presenter {
        fun loadReservations()
        fun verifyUserIsLoggedIn(): Boolean
        fun confirmDeletion(rid: String)
    }

    interface Interactor {
        fun fetchReservations()
        fun getReservation(rid: String): Task<DocumentSnapshot>
    }

    interface CompleteListener {
        fun onSuccess(reservation: ArrayList<CardObject>)
        fun onFailure()
        fun confirmDeletion(rid: String)
    }
}