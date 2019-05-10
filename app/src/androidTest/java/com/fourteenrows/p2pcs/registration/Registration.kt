package com.fourteenrows.p2pcs.registration

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.UITest
import com.fourteenrows.p2pcs.activities.authentication.registration.RegistrationView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Registration : UITest() {
    @get:Rule
    var activityRule: RegistartionRule<RegistrationView> = RegistartionRule(RegistrationView::class.java)

    private fun emptyExept(itemToRemove: Int) {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.registrationName, "test")
        map.put(R.id.registrationSurname, "test")
        map.put(R.id.registrationEmail, "test@test.com")
        map.put(R.id.registrationPassword1, "password")
        map.put(R.id.registrationPassword2, "password")

        map.remove(itemToRemove)

        writeEditTextAndGetDialogWithId(
            map,
            R.id.registrationButton,
            "Compila tutti i campi"
        )
    }

    @Test
    fun emptyName() {
        emptyExept(R.id.registrationName)
    }

    @Test
    fun emptySurname() {
        emptyExept(R.id.registrationSurname)
    }

    @Test
    fun emptyEmail() {
        emptyExept(R.id.registrationEmail)
    }

    @Test
    fun emptyPassword1() {
        emptyExept(R.id.registrationPassword1)
    }

    @Test
    fun emptyPassword2() {
        emptyExept(R.id.registrationPassword2)
    }

    @Test
    fun passwordLength() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.registrationName, "test")
        map.put(R.id.registrationSurname, "test")
        map.put(R.id.registrationEmail, "test@test.com")
        map.put(R.id.registrationPassword1, "passw")
        map.put(R.id.registrationPassword2, "passw")

        writeEditTextAndGetDialogWithId(
            map,
            R.id.registrationButton,
            "La password deve essere di almeno 6 caratteri"
        )
    }

    @Test
    fun emailAlreadyPresent() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.registrationName, "test")
        map.put(R.id.registrationSurname, "test")
        map.put(R.id.registrationEmail, "fourteenrows@gmail.com")
        map.put(R.id.registrationPassword1, "password")
        map.put(R.id.registrationPassword2, "password")

        writeEditTextAndGetDialogWithId(
            map,
            R.id.registrationButton,
            "Email già presente nel sistema"
        )
    }

    @Test
    fun invalidEmail() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.registrationName, "test")
        map.put(R.id.registrationSurname, "test")
        map.put(R.id.registrationEmail, "test")
        map.put(R.id.registrationPassword1, "password")
        map.put(R.id.registrationPassword2, "password")

        writeEditTextAndGetDialogWithId(
            map,
            R.id.registrationButton,
            "L'email inserita non rappresenta una email"
        )
    }

    @Test
    fun differentPasswords() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.registrationName, "test")
        map.put(R.id.registrationSurname, "test")
        map.put(R.id.registrationEmail, "test@test.com")
        map.put(R.id.registrationPassword1, "passworq")
        map.put(R.id.registrationPassword2, "password")

        writeEditTextAndGetDialogWithId(
            map,
            R.id.registrationButton,
            "Le password non combaciano"
        )
    }

    @Test
    fun vaildRegistration() {
        val email = generateEmail()
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.registrationName, "test")
        map.put(R.id.registrationSurname, "test")
        map.put(R.id.registrationEmail, email)
        map.put(R.id.registrationPassword1, "password")
        map.put(R.id.registrationPassword2, "password")

        writeEditTextAndGetDialogWithId(
            map,
            R.id.registrationButton,
            "Registrazione avvenuta correttamente! Controlla la tua casella email per continuare"
        )
    }
}