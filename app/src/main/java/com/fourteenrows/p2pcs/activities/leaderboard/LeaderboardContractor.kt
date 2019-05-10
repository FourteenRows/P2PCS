package com.fourteenrows.p2pcs.activities.leaderboard

import com.fourteenrows.p2pcs.generalview.IView

interface LeaderboardContractor {
    interface View : IView

    interface Presenter

    interface Interactor

    interface CompleteListener
}