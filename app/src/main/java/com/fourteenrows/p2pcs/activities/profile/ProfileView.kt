package com.fourteenrows.p2pcs.activities.profile

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.leaderboard.LeaderboardView
import com.fourteenrows.p2pcs.activities.profile.ProfileContractor.View
import com.fourteenrows.p2pcs.menu.DrawerUtil
import com.fourteenrows.p2pcs.model.User
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileView : AppCompatActivity(), View {

    private lateinit var presenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val toolBar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        toolBar.setOnMenuItemClickListener {
            val intent = Intent(this, LeaderboardView::class.java)
            startActivity(intent)
            true
        }
        DrawerUtil.getDrawer(this, toolBar)

        presenter = ProfilePresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun initView() {
        profileName.setOnClickListener {
            makeEditTextDialog(
                "Nome",
                "Modifica il nome",
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME,
                R.string.confirm,
                profileName.text.toString(),
                "name"
            )
        }
        profileSurname.setOnClickListener {
            makeEditTextDialog(
                "Cognome",
                "Modifica il cognome",
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME,
                R.string.confirm,
                profileSurname.text.toString(),
                "surname"
            )
        }
        changePassword.setOnClickListener {
            presenter.sendReset()
        }
    }

    override fun makeEditTextDialog(
        hint: String,
        title: String,
        type: Int,
        positive: Int,
        placeholder: String,
        field: String
    ) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_text, null)
        val textField: EditText = dialogView.findViewById(R.id.alertTextField) as EditText
        textField.hint = hint
        textField.inputType = type
        textField.setText(placeholder)
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setView(dialogView)
            .setPositiveButton(positive) { _, _ ->
                val input = textField.text.toString()
                presenter.updateData(field, input)
            }
            .setNeutralButton(R.string.cancel) { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun replaceData(user: User) {
        profileName.text = user.name
        profileSurname.text = user.surname
        profileEmail.text = user.mail
    }

    override fun makeAlertDialog(message: Int, title: Int) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun refresh() {
        val intent = intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }
}