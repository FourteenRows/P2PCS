package com.fourteenrows.p2pcs.activities.reservation

import com.fourteenrows.p2pcs.activities.reservation.ReservationContractor.CompleteListener
import com.fourteenrows.p2pcs.activities.reservation.ReservationContractor.Interactor
import com.fourteenrows.p2pcs.model.*
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ReservationInteractor(private val listener: CompleteListener) : Interactor {
    //map: mappa ordinata di prenotazioni dalle più alle meno recenti
    private fun getReservations(map: ArrayList<Map<String, Any>>): ArrayList<CardObject> {
        val array = ArrayList<CardObject>()
        if (map.size == 0 || ModelDates.isInThePast(map[0]["start_slot"] as Timestamp)) {
            array.add(MessageErrorObject(ReservationCardType.TITLE_MESSAGE_ERROR))
        } else array.add(CardObject(ReservationCardType.TITLE_ACTIVE_PRENOTATION))

        var newIntoPast = false
        map.forEach {
            val startSlot = it["start_slot"] as Timestamp
            if (!ModelDates.isInThePast(startSlot)) {
                array.add(
                    ActiveReservationObject(
                        ReservationCardType.TITLE_ACTIVE_PRENOTATION
                        , it["model"] as String, ModelDates.toLocaleTimeFormat(startSlot.toDate()),
                        ModelDates.getSlotString(startSlot), it["rid"] as String
                    )
                )
            } else {
                if (!newIntoPast) {
                    newIntoPast = true
                    array.add(CardObject(ReservationCardType.TITLE_PAST_PRENOTATION))
                }
                array.add(
                    //TODO(Set endDate, get total cost")
                    PastReservationObject(
                        ReservationCardType.TITLE_PAST_PRENOTATION,
                        it["model"] as String, ModelDates.toLocaleTimeFormat(startSlot.toDate()),
                        ModelDates.getSlotString(startSlot), it["rid"] as String, 999.99
                    )
                )
            }
        }
        return array
    }

    override fun fetchReservations() {
        val uid = ModelFirebase.getUid()
        FirebaseFirestore.getInstance().collection("Dates")
            .whereEqualTo("owner", uid)
            .orderBy("start_slot", Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val array = ArrayList<Map<String, Any>>()
                    it.result!!.forEach { x ->
                        val map = x.data.toMap() as MutableMap
                        map.put("rid", x.id)
                        array.add(map)
                    }
                    listener.onSuccess(getReservations(array))
                } else listener.onFailure()
            }
    }

    override fun getReservation(rid: String): Task<DocumentSnapshot> {
        return ModelFirebase.getReservation(rid)
    }
}