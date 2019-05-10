package com.fourteenrows.p2pcs.activities.authentication.login

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.authentication.login.LoginContractor.View
import com.fourteenrows.p2pcs.activities.authentication.registration.RegistrationView
import com.fourteenrows.p2pcs.activities.reservation.ReservationView
import kotlinx.android.synthetic.main.activity_login.*

class LoginView : AppCompatActivity(), View {

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this)
    }

    override fun initView() {
        forgot.setOnClickListener {
            makeEditTextDialog()
        }

        loginButton.setOnClickListener {
            val email = loginEmail.text.toString()
            val pwd = loginPassword.text.toString()
            presenter.checkLoginValues(email, pwd)
        }

        notRegistered.setOnClickListener {
            val intent = Intent(this, RegistrationView::class.java)
            startActivity(intent)
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

    override fun makeEditTextDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_text, null)
        val textField = dialogView.findViewById(R.id.alertTextField) as EditText
        textField.setHint(R.string.email)
        val builder = AlertDialog.Builder(this)
            .setTitle(R.string.password_reset)
            .setView(dialogView)
            .setPositiveButton(R.string.reset) { _, _ ->
                val text = textField.text.toString()
                presenter.checkResetValues(text)
            }
            .setNeutralButton(R.string.cancel) { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun updateViewData() {
        val intent = Intent(this, ReservationView::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}