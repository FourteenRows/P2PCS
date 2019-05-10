package com.fourteenrows.p2pcs.activities.quest

import com.fourteenrows.p2pcs.generalview.IView

interface QuestContractor {
    interface View : IView

    interface Presenter

    interface Interactor

    interface CompleteListener
}