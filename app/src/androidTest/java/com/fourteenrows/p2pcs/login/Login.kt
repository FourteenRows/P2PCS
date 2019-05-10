package com.fourteenrows.p2pcs.login

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.UITest
import com.fourteenrows.p2pcs.activities.authentication.login.LoginView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Login : UITest() {

    @get:Rule
    var activityRule: LoginRule<LoginView> =
        LoginRule(LoginView::class.java)

    @Test
    fun wrongCredentials() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.loginEmail, "fourteenrows@gmail.com")
        map.put(R.id.loginPassword, "wrong")

        writeEditTextAndGetDialogWithId(
            map,
            R.id.loginButton,
            "Credenziali errate"
        )
    }

    @Test
    fun invalidEmail() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.loginEmail, "not vaild mail")
        map.put(R.id.loginPassword, "wrong")

        writeEditTextAndGetDialogWithId(
            map,
            R.id.loginButton,
            "L'email inserita non rappresenta una email"
        )
    }

    @Test
    fun emailNotCompiled() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.loginPassword, "wrong")

        writeEditTextAndGetDialogWithId(
            map,
            R.id.loginButton,
            "Compila tutti i campi"
        )
    }

    @Test
    fun passwordNotCompiled() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.loginEmail, "fourteenrows@gmail.com")

        writeEditTextAndGetDialogWithId(
            map,
            R.id.loginButton,
            "Compila tutti i campi"
        )
    }

    @Test
    fun notVerifiedEmail() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.loginEmail, "accveri@gmail.com")
        map.put(R.id.loginPassword, "dtrump")

        writeEditTextAndGetDialogWithId(
            map,
            R.id.loginButton,
            "Account non verificato, controlla la tua casella email"
        )
    }

    @Test
    fun validLogin() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.loginEmail, "fourteenrows@gmail.com")
        map.put(R.id.loginPassword, "testacc")

        writeEditTextAndChangeView(
            map,
            R.id.loginButton,
            R.id.leaderboardButton
        )
    }

    @Test
    fun vaildResetPassword() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.alertTextField, generateEmail())

        writeEditTextInAlertAndGetDialog(
            map,
            R.id.forgot,
            "RESET",
            "Email di reset inviata"
        )
    }

    @Test
    fun emptyEmailResetPassword() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.alertTextField, "")

        writeEditTextInAlertAndGetDialog(
            map,
            R.id.forgot,
            "RESET",
            "Compila il campo"
        )
    }

    @Test
    fun notEmailResetPassword() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.alertTextField, "test")

        writeEditTextInAlertAndGetDialog(
            map,
            R.id.forgot,
            "RESET",
            "L'email inserita non rappresenta una email"
        )
    }
}