package com.fourteenrows.p2pcs.activities.car.add_car

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.car.CarView
import com.fourteenrows.p2pcs.activities.car.add_car.AddCarContractor.Presenter
import com.fourteenrows.p2pcs.activities.car.add_car.AddCarContractor.View
import com.fourteenrows.p2pcs.activities.leaderboard.LeaderboardView
import com.fourteenrows.p2pcs.menu.DrawerUtil
import kotlinx.android.synthetic.main.activity_add_car.*
import java.util.*

class AddCarView : AppCompatActivity(), View {

    private lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)

        val toolBar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        toolBar.setOnMenuItemClickListener {
            val intent = Intent(this, LeaderboardView::class.java)
            startActivity(intent)
            true
        }
        DrawerUtil.getDrawer(this, toolBar)

        presenter = AddCarPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun initView() {
        clearEndDate.setOnClickListener {
            vehicleDate.setText("")
        }

        vehicleConfirm.setOnClickListener {
            val plate = vehiclePlate.text.toString().toUpperCase()
            val model = vehicleModel.text.toString()
            val seats = vehicleSeats.text.toString()
            val endDate = vehicleMillis.text.toString()

            presenter.checkVehicleValues(plate, model, seats, endDate)
        }

        vehicleDate.setOnClickListener {
            makeCalendarDialog()
        }
    }

    override fun makeAlertDialog(message: Int, title: Int) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun makeCalendarDialog() {
        val calendar = Calendar.getInstance()

        val dateListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            vehicleDate.showSoftInputOnFocus = false
            val date = presenter.formatDate(calendar.time)
            vehicleDate.setText(date)
            vehicleMillis.text = calendar.timeInMillis.toString()
        }

        val datePicker = DatePickerDialog(
            this, dateListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.datePicker.minDate = presenter.nextNDays(1)
        datePicker.datePicker.maxDate = presenter.nextNDays(31)

        datePicker.show()
    }

    override fun changeView(message: Int, title: Int) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { _, _ -> }
            .setOnDismissListener {
                val intent = Intent(this, CarView::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}