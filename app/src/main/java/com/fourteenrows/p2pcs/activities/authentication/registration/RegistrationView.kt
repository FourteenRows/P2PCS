package com.fourteenrows.p2pcs.activities.authentication.registration

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.authentication.login.LoginView
import com.fourteenrows.p2pcs.activities.authentication.registration.RegistrationContractor.View
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationView : AppCompatActivity(), View {

    private lateinit var presenter: RegistrationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        presenter = RegistrationPresenter(this)
    }

    override fun initView() {
        alreadyRegistered.setOnClickListener {
            updateViewData()
        }

        forgot.setOnClickListener {
            makeEditTextDialog()
        }

        registrationButton.setOnClickListener {
            val email = registrationEmail.text.toString()
            val name = registrationName.text.toString()
            val surname = registrationSurname.text.toString()
            val password = registrationPassword1.text.toString()
            val password2 = registrationPassword2.text.toString()

            presenter.checkLoginValues(email, name, surname, password, password2)
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
        val intent = Intent(this, LoginView::class.java)
        startActivity(intent)
    }
}