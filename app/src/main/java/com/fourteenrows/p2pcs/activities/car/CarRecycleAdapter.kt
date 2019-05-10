package com.fourteenrows.p2pcs.activities.car

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.model.FetchedVehicle
import com.fourteenrows.p2pcs.model.ModelUtils
import kotlinx.android.synthetic.main.recycler_car_item.view.*
import java.util.*

class CarRecycleAdapter(
    private val items: ArrayList<FetchedVehicle>,
    private var listener: CarContractor.CompleteListener
) :
    RecyclerView.Adapter<CarRecycleAdapter.BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_car_item, parent, false)
        return VehicleObjectViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = items[position]
        (holder as VehicleObjectViewHolder).bind(element)
    }

    override fun getItemCount(): Int = items.size

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    class VehicleObjectViewHolder(val view: View, val listener: CarContractor.CompleteListener) :
        BaseViewHolder<FetchedVehicle>(view),
        View.OnClickListener {
        private val vehicleModel: TextView = view.vehicleModel
        private val vehicleSeats: TextView = view.seats
        private val vehiclePlate: TextView = view.plate
        private val vehicleInstant: TextView = view.instant
        private val uid: TextView = view.uid
        private val finalAvailability: TextView = view.vehicleAvailability

        override fun bind(item: FetchedVehicle) {
            vehicleModel.text = item.model
            vehicleSeats.text = item.seats.toString()
            vehiclePlate.text = item.plate

            if (item.final_availability!! == Date(32500915200000)) {
                finalAvailability.text = ""
                finalAvailability.width = 0
                finalAvailability.height = 0
            } else {
                val date = ModelUtils.formatDate(Date(item.final_availability.time))
                finalAvailability.text = "Non prenotabile dopo il $date"
            }

            if (item.instant_availability) {
                vehicleInstant.setText(R.string.vehicle_available)
                vehicleInstant.setTextColor(Color.BLACK)
            } else {
                vehicleInstant.setText(R.string.vehicle_unavailable)
                vehicleInstant.setTextColor(Color.RED)
            }
            uid.text = item.cid
            view.deleteVehicle.setOnClickListener {
                listener.checkReservations(uid.text.toString())
            }
            view.editVehicle.setOnClickListener {
                listener.editVehicle(item)
            }
        }

        override fun onClick(view: View) {
        }
    }
}