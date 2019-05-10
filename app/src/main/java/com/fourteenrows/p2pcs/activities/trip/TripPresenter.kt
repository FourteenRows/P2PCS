package com.fourteenrows.p2pcs.activities.trip

import com.fourteenrows.p2pcs.activities.trip.TripContractor.*

class TripPresenter(toView: View) : Presenter, CompleteListener {

    private var view = toView
    private val interactor = TripInteractor(this)

    init {
        view.initView()
    }

}