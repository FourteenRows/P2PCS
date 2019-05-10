package com.fourteenrows.p2pcs.activities.avatar

import com.fourteenrows.p2pcs.activities.avatar.AvatarContractor.*

class AvatarPresenter(toView: View) : Presenter, CompleteListener {

    private var view = toView
    private val interactor = AvatarInteractor(this)

    init {
        view.initView()
    }

}