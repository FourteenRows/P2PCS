package com.fourteenrows.p2pcs.activities.quest

import com.fourteenrows.p2pcs.activities.quest.QuestContractor.*

class QuestPresenter(toView: View) : Presenter, CompleteListener {
    private var view = toView
    private val interactor = QuestInteractor(this)

    init {
        view.initView()
    }

}