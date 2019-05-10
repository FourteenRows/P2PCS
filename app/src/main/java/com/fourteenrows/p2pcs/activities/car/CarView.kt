package com.fourteenrows.p2pcs.activities.car

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.car.CarContractor.Presenter
import com.fourteenrows.p2pcs.activities.car.CarContractor.View
import com.fourteenrows.p2pcs.activities.car.add_car.AddCarView
import com.fourteenrows.p2pcs.activities.car.edit_car.EditCarView
import com.fourteenrows.p2pcs.activities.leaderboard.LeaderboardView
import com.fourteenrows.p2pcs.menu.DrawerUtil
import com.fourteenrows.p2pcs.model.FetchedVehicle
import kotlinx.android.synthetic.main.activity_car.*

class CarView : AppCompatActivity(), View {

    private lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        val toolBar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        toolBar.setOnMenuItemClickListener {
            val intent = Intent(this, LeaderboardView::class.java)
            startActivity(intent)
            true
        }
        DrawerUtil.getDrawer(this, toolBar)

        presenter = CarPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun initView() {
        recycleView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        addVehicle.setOnClickListener {
            val intent = Intent(this, AddCarView::class.java)
            startActivity(intent)
        }
    }

    override fun editVehicle(car: FetchedVehicle) {
        val intent = Intent(this, EditCarView::class.java)
        intent.putExtra("instant_availability", car.instant_availability)
        intent.putExtra("final_availability", car.final_availability?.time)
        intent.putExtra("model", car.model)
        intent.putExtra("owner", car.owner)
        intent.putExtra("plate", car.plate)
        intent.putExtra("seats", car.seats)
        intent.putExtra("carId", car.cid)
        startActivity(intent)
    }

    override fun makeAlertDialog(message: Int, title: Int) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun makeConfirmationDialog(value: String) {
        val builder = AlertDialog.Builder(this)
            .setTitle(R.string.vehicle_delete)
            .setMessage(R.string.confirmation_vehicle)
            .setPositiveButton(R.string.confirm) { _, _ ->
                presenter.deleteCar(value)
            }
            .setNeutralButton(R.string.cancel) { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun setRecyclerAdapter(adapter: CarRecycleAdapter) {
        recycleView.adapter = adapter
    }

}