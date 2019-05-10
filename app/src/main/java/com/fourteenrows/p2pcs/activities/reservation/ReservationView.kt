package com.fourteenrows.p2pcs.activities.reservation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.authentication.login.LoginView
import com.fourteenrows.p2pcs.activities.leaderboard.LeaderboardView
import com.fourteenrows.p2pcs.activities.reservation.ReservationContractor.View
import com.fourteenrows.p2pcs.activities.reservation.add_reservation.AddReservationView
import com.fourteenrows.p2pcs.menu.DrawerUtil
import kotlinx.android.synthetic.main.activity_reservation.*

class ReservationView : AppCompatActivity(), View {

    private lateinit var presenter: ReservationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        presenter = ReservationPresenter(this)
        if (presenter.verifyUserIsLoggedIn()) {
            val toolBar: Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolBar)
            toolBar.setOnMenuItemClickListener {
                val intent = Intent(this, LeaderboardView::class.java)
                startActivity(intent)
                true
            }
            DrawerUtil.getDrawer(this, toolBar)

            presenter.loadReservations()
        } else {
            val intent = Intent(this, LoginView::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun initView() {
        recycleView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        addReservation.setOnClickListener {
            val intent = Intent(this, AddReservationView::class.java)
            startActivity(intent)
        }
    }

    override fun makeAlertDialog(message: Int, title: Int) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun makeConfirmationDialog(rid: String) {
        val builder = AlertDialog.Builder(this)
            .setTitle("Elimina auto")
            .setMessage(R.string.confirmation_reservation)
            .setPositiveButton(R.string.confirm) { _, _ ->
                presenter.deleteReservation(rid)
            }
            .setNeutralButton(R.string.cancel) { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun resetView() {
        val intent = Intent(this, LoginView::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun setRecyclerAdapter(adapter: ReservationRecyclerAdapter) {
        recycleView.adapter = adapter
    }
}