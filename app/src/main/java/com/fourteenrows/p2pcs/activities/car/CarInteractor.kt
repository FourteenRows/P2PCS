package com.fourteenrows.p2pcs.activities.car

import com.fourteenrows.p2pcs.activities.car.CarContractor.CompleteListener
import com.fourteenrows.p2pcs.activities.car.CarContractor.Interactor
import com.fourteenrows.p2pcs.model.FetchedVehicle
import com.fourteenrows.p2pcs.model.ModelFirebase
import com.google.firebase.Timestamp
import java.util.*
import kotlin.collections.ArrayList

class CarInteractor(private val listener: CompleteListener) : Interactor {

    private fun getVehicles(map: ArrayList<Map<String, Any>>): ArrayList<FetchedVehicle> {
        val vehicles = ArrayList<FetchedVehicle>()
        val uid = getUid()!!
        map.forEach {
            vehicles.add(
                FetchedVehicle(
                    Date((it["final_availability"] as Timestamp).toDate().time),
                    it["model"] as String,
                    uid,
                    it["plate"] as String,
                    it["seats"] as Long, it["uid"] as String,
                    it["instant_availability"] as Boolean
                )
            )
        }
        return vehicles
    }

    private fun getUid(): String? = ModelFirebase.getUid()

    override fun fetchVehicles() {
        ModelFirebase.getUserVechicles()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result != null) {
                        val array = ArrayList<Map<String, Any>>()
                        it.result!!.forEach { car ->
                            val map = car.data.toMap() as MutableMap
                            map["uid"] = car.id
                            array.add(map)
                        }
                        listener.onSuccess(getVehicles(array))
                    }
                } else {
                    listener.onFailure()
                }
            }
    }
}