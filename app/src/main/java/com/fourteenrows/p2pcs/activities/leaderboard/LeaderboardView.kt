package com.fourteenrows.p2pcs.activities.leaderboard

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.leaderboard.LeaderboardContractor.View
import com.fourteenrows.p2pcs.menu.DrawerUtil

class LeaderboardView : AppCompatActivity(), View {

    private lateinit var presenter: LeaderboardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val toolBar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        DrawerUtil.getDrawer(
            this, toolBar
        )

        presenter = LeaderboardPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun initView() {

    }
}