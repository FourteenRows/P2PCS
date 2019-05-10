package com.fourteenrows.p2pcs.activities.reservation.add_reservation

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.leaderboard.LeaderboardView
import com.fourteenrows.p2pcs.activities.reservation.ReservationView
import com.fourteenrows.p2pcs.activities.reservation.add_reservation.AddReservationContractor.View
import com.fourteenrows.p2pcs.activities.reservation.add_reservation.add_reservation_vehicle.AddReservationVehicleView
import com.fourteenrows.p2pcs.menu.DrawerUtil
import com.fourteenrows.p2pcs.model.ModelDates
import com.fourteenrows.p2pcs.model.ModelUtils
import kotlinx.android.synthetic.main.activity_add_reservation.*
import java.text.SimpleDateFormat
import java.util.*

class AddReservationView : AppCompatActivity(), View {

    private lateinit var presenter: AddReservationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reservation)

        val toolBar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        toolBar.setOnMenuItemClickListener {
            val intent = Intent(this, LeaderboardView::class.java)
            startActivity(intent)
            true
        }
        DrawerUtil.getDrawer(this, toolBar)

        presenter = AddReservationPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun initView() {

        reservationDate.setOnClickListener {
            makeCalendarDialog()
        }

        reservationCar.setOnClickListener {
            val date = reservationMillis.text.toString()
            val zone = reservationZone.text.toString()
            presenter.checkValues(date, zone)
        }

        reservationConfirm.setOnClickListener {
            presenter.insertReservation()
        }

        reservationZone.setOnClickListener {
            makeRadioDialog()
        }
    }

    override fun changeView(message: Int, title: Int) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> }
            .setOnDismissListener {
                val intent = Intent(this, ReservationView::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun chooseVehicle(date: String, zone: String) {
        val intent = Intent(this, AddReservationVehicleView::class.java)
        intent.putExtra("date", date)
        intent.putExtra("zone", zone)
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

    override fun makeCalendarDialog() {
        val calendar = Calendar.getInstance()

        val dateListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)


            if (intent.getStringExtra("carId") != null) {
                if (calendar.timeInMillis != intent.getStringExtra("date").toLong()) {
                    reservationCar.setText("")
                    intent.removeExtra("carId")
                    intent.removeExtra("model")
                }
            }

            val dateFormat = "dd MMMM yyyy"
            val toLocale = SimpleDateFormat(dateFormat, Locale.ITALIAN)
            reservationDate.setText(toLocale.format(calendar.time))
            reservationDate.showSoftInputOnFocus = false
            reservationMillis.text = calendar.timeInMillis.toString()
        }

        val datePicker = DatePickerDialog(
            this, dateListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.datePicker.minDate = ModelDates.nextNDays(1)
        datePicker.datePicker.maxDate = ModelDates.nextNDays(31)
        datePicker.show()
    }

    override fun makeRadioDialog() {
        val listItems = ModelUtils.getTimeSlots()
        val builder = AlertDialog.Builder(this)
            .setTitle("Scegli fascia")
            .setItems(listItems) { _, item ->
                if (intent.getStringExtra("carId") != null) {
                    if (listItems[item] != intent.getStringExtra("zone")) {
                        reservationCar.setText("")
                        intent.removeExtra("carId")
                        intent.removeExtra("model")
                    }
                }

                reservationZone.setText(listItems[item])
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun refillContent(carId: String, date: Long, zone: String) {
        reservationCar.setText(carId)
        reservationMillis.text = date.toString()

        val dateFormat = "dd MMMM yyyy"
        val toLocale = SimpleDateFormat(dateFormat, Locale.ITALIAN)
        reservationDate.setText(toLocale.format(date))

        reservationZone.setText(zone)
    }

    override fun refresh() {
        val intent = intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        startActivity(intent)
    }
}