package com.fourteenrows.p2pcs.activities.shop

import com.fourteenrows.p2pcs.activities.shop.ShopContractor.*

class ShopPresenter(toView: View) : Presenter, CompleteListener {

    private var view = toView
    private val interactor = ShopInteractor(this)

    init {
        view.initView()
    }

}