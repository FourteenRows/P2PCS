package com.fourteenrows.p2pcs.activities.reservation.add_reservation.add_reservation_vehicle

import com.fourteenrows.p2pcs.activities.reservation.add_reservation.add_reservation_vehicle.AddReservationVehicleContractor.CompleteListener
import com.fourteenrows.p2pcs.activities.reservation.add_reservation.add_reservation_vehicle.AddReservationVehicleContractor.Interactor
import com.fourteenrows.p2pcs.model.ModelDates
import com.fourteenrows.p2pcs.model.ModelFirebase
import com.fourteenrows.p2pcs.model.ModelValidator

class AddReservationVehicleInteractor(val listener: CompleteListener) : Interactor {

    override fun fetchVehicles(date: Long, timeSlot: String) {
        ModelFirebase.fetchAvailableVehicles(ModelDates.nextDayOf(date, timeSlot))
            .addOnSuccessListener { it ->
                val vehicles = ArrayList<Map<String, Any>>()
                it.documents.forEach {
                    val map = it.data!!.toMap() as MutableMap
                    map["cid"] = it.id
                    vehicles.add(map)
                }
                ModelFirebase.fetchBusyVehicleOf(date, timeSlot)
                    .addOnSuccessListener { it2 ->
                        it2.documents.forEach {
                            // Remove alredy reserved cars
                            vehicles.removeIf { v ->
                                v.getValue("cid").equals(it["car"])
                            }
                        }
                        vehicles.removeIf { v ->
                            // Remove user's car
                            v.getValue("owner").equals(ModelFirebase.getUid())
                        }
                        listener.onSuccess(getVehicles(vehicles))
                    }
            }
    }

    private fun getVehicles(map: ArrayList<Map<String, Any>>): ArrayList<VehicleObject> {
        val vehicles = ArrayList<VehicleObject>()
        map.forEach {
            vehicles.add(
                VehicleObject(
                    it["model"] as String, it["seats"] as Long, it["cid"] as String
                )
            )
        }
        return vehicles
    }

    override fun checkValueIsEmpty(input: String): Boolean = ModelValidator.checkValueIsEmpty(input)
}