package com.fourteenrows.p2pcs.activities.leaderboard

import com.fourteenrows.p2pcs.activities.leaderboard.LeaderboardContractor.*

class LeaderboardPresenter(toView: View) : Presenter, CompleteListener {

    private var view = toView
    private val interactor = LeaderboardInteractor(this)

    init {
        view.initView()
    }

}