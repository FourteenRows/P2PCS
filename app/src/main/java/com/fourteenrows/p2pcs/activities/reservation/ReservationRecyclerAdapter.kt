package com.fourteenrows.p2pcs.activities.reservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.model.*
import kotlinx.android.synthetic.main.recycler_reservation_active_reservation_item.view.*
import kotlinx.android.synthetic.main.recycler_reservation_active_reservation_item.view.endDate
import kotlinx.android.synthetic.main.recycler_reservation_active_reservation_item.view.hours
import kotlinx.android.synthetic.main.recycler_reservation_active_reservation_item.view.vehicleModel
import kotlinx.android.synthetic.main.recycler_reservation_past_reservation_item.view.*
import kotlinx.android.synthetic.main.recycler_reservation_title_item.view.*

class ReservationRecyclerAdapter(
    private val items: ArrayList<CardObject>,
    private val listener: ReservationContractor.CompleteListener
) :
    RecyclerView.Adapter<ReservationRecyclerAdapter.BaseViewHolder<*>>() {
    companion object {
        private const val TYPE_TITLE = 0
        private const val TYPE_ACTIVE_RESERVATION = 1
        private const val TYPE_PAST_RESERVATION = 2
        private const val TYPE_MESSAGE_ERROR = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_MESSAGE_ERROR -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_reservation_title_item, parent, false)
                MessageErrorViewHolder(view)
            }
            TYPE_TITLE -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.recycler_reservation_title_item, parent, false)
                TitleViewHolder(view)
            }
            TYPE_ACTIVE_RESERVATION -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_reservation_active_reservation_item, parent, false)
                ActiveReservationViewHolder(view, listener)
            }
            TYPE_PAST_RESERVATION -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_reservation_past_reservation_item, parent, false)
                PastReservationViewHolder(view, listener)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = items[position]
        when (holder) {
            is PastReservationViewHolder -> holder.bind(element as PastReservationObject)
            is ActiveReservationViewHolder -> holder.bind(element as ActiveReservationObject)
            is MessageErrorViewHolder -> holder.bind(element as MessageErrorObject)
            is TitleViewHolder -> holder.bind(element)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is PastReservationObject -> TYPE_PAST_RESERVATION
            is ActiveReservationObject -> TYPE_ACTIVE_RESERVATION
            is MessageErrorObject -> TYPE_MESSAGE_ERROR
            else -> TYPE_TITLE
        }
    }

    override fun getItemCount(): Int = items.size

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    class TitleViewHolder(val view: View) : BaseViewHolder<CardObject>(view) {
        private val titleTextView = view.title

        override fun bind(item: CardObject) {
            if (item.cardType == ReservationCardType.TITLE_ACTIVE_PRENOTATION) {
                titleTextView.setText(R.string.future_reservations)
            } else {
                titleTextView.setText(R.string.past_reservations)
            }
        }
    }

    class MessageErrorViewHolder(val view: View) : BaseViewHolder<CardObject>(view) {
        private val titleTextView = view.title

        override fun bind(item: CardObject) {
            titleTextView.setText(R.string.missing_reservations)
        }
    }

    class ActiveReservationViewHolder(val view: View, val listener: ReservationContractor.CompleteListener) :
        BaseViewHolder<ActiveReservationObject>(view),
        View.OnClickListener {
        private val vehicleModel: TextView = view.vehicleModel
        private val endDate: TextView = view.endDate
        private val hours: TextView = view.hours
        private val rid = view.rida

        override fun bind(item: ActiveReservationObject) {
            vehicleModel.text = item.vehicleModel
            endDate.text = item.endDate
            hours.text = item.hours
            rid.text = item.rid

            view.deleteReservation.setOnClickListener {
                listener.confirmDeletion(rid.text.toString())
            }
        }

        override fun onClick(view: View) {
        }
    }

    class PastReservationViewHolder(val view: View, val listener: ReservationContractor.CompleteListener) :
        BaseViewHolder<PastReservationObject>(view),
        View.OnClickListener {
        private val vehicleModel: TextView = view.vehicleModel
        private val endDate: TextView = view.endDate
        private val hours: TextView = view.hours
        private val totalCost = view.totalCost
        private val rid = view.ridp

        override fun bind(item: PastReservationObject) {
            vehicleModel.text = item.vehicleModel
            endDate.text = item.endDate
            hours.text = item.hours
            totalCost.text = item.totalCost.toString()
            rid.text = item.rid
        }

        override fun onClick(view: View) {
        }
    }
}