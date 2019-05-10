package com.fourteenrows.p2pcs.activities.avatar

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.avatar.AvatarContractor.Presenter
import com.fourteenrows.p2pcs.activities.avatar.AvatarContractor.View
import com.fourteenrows.p2pcs.activities.leaderboard.LeaderboardView
import com.fourteenrows.p2pcs.menu.DrawerUtil

class AvatarView : AppCompatActivity(), View {

    private lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar)

        val toolBar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        toolBar.setOnMenuItemClickListener {
            val intent = Intent(this, LeaderboardView::class.java)
            startActivity(intent)
            true
        }
        DrawerUtil.getDrawer(this, toolBar)

        presenter = AvatarPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun initView() {
    }
}