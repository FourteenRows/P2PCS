package com.fourteenrows.p2pcs.activities.booster

import com.fourteenrows.p2pcs.activities.booster.BoosterContractor.*

class BoosterPresenter(toView: View) : Presenter, CompleteListener {

    private var view = toView
    private val interactor = BoosterInteractor(this)

    init {
        view.initView()
    }

}