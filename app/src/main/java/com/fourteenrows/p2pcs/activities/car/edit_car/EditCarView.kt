package com.fourteenrows.p2pcs.activities.car.edit_car

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.car.CarView
import com.fourteenrows.p2pcs.activities.car.edit_car.EditCarContractor.Presenter
import com.fourteenrows.p2pcs.activities.car.edit_car.EditCarContractor.View
import com.fourteenrows.p2pcs.activities.leaderboard.LeaderboardView
import com.fourteenrows.p2pcs.menu.DrawerUtil
import kotlinx.android.synthetic.main.activity_edit_car.*
import java.text.SimpleDateFormat
import java.util.*

class EditCarView : AppCompatActivity(), View {

    private lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_car)

        val toolBar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        toolBar.setOnMenuItemClickListener {
            val intent = Intent(this, LeaderboardView::class.java)
            startActivity(intent)
            true
        }
        DrawerUtil.getDrawer(this, toolBar)

        presenter = EditCarPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun initView() {
        clearEndDate.setOnClickListener {
            editVehicleDate.setText("")
            editVehicleMillis.text = ""
        }

        editVehicleConfirm.setOnClickListener {
            val plate = editVehiclePlate.text.toString().toUpperCase()
            val model = editVehicleModel.text.toString()
            val seats = editVehicleSeats.text.toString()
            val endDate = editVehicleMillis.text.toString()
            val nowDate = editVehicleInstant.isChecked

            presenter.checkVehicleValues(plate, model, seats, endDate, nowDate)
        }

        editVehicleDate.setOnClickListener {
            makeCalendarDialog()
        }
    }

    override fun adapt(
        final: Date?,
        instant: Boolean,
        model: String,
        plate: String,
        seats: Long
    ) {
        if (final != null && final != Date(32500915200000)) {
            val dateFormat = "dd MMMM yyyy"
            val toLocale = SimpleDateFormat(dateFormat, Locale.ITALIAN)
            editVehicleDate.setText(toLocale.format(final))
            editVehicleMillis.text = final.time.toString()
        }

        editVehicleModel.setText(model)
        editVehiclePlate.setText(plate)
        editVehicleSeats.setText(seats.toString())
        editVehicleInstant.isChecked = instant
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

            editVehicleDate.showSoftInputOnFocus = false
            val date = presenter.formatDate(calendar.time)
            editVehicleDate.setText(date)
            editVehicleMillis.text = calendar.timeInMillis.toString()
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
