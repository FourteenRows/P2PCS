package com.fourteenrows.p2pcs.activities.trip

import com.fourteenrows.p2pcs.activities.trip.TripContractor.CompleteListener
import com.fourteenrows.p2pcs.activities.trip.TripContractor.Interactor

class TripInteractor(private val listener: CompleteListener) : Interactor