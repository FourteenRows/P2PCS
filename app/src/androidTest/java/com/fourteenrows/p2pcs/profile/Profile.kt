package com.fourteenrows.p2pcs.profile

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.UITest
import com.fourteenrows.p2pcs.activities.profile.ProfileView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class Profile : UITest() {
    @get:Rule
    var activityRule: ProfileRule<ProfileView> =
        ProfileRule(ProfileView::class.java)

    @Test
    fun emptyName() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.alertTextField, "")

        writeEditTextInAlertAndGetDialog(
            map,
            R.id.profileName,
            "CONFERMA",
            "Compila il campo"
        )
    }

    @Test
    fun emptySurname() {
        val map: MutableMap<Int, String> = mutableMapOf()

        map.put(R.id.alertTextField, "")

        writeEditTextInAlertAndGetDialog(
            map,
            R.id.profileSurname,
            "CONFERMA",
            "Compila il campo"
        )
    }

    @Test
    fun sendEmailPassword() {
        sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.changePassword))
            .perform((ViewActions.click()))
        Espresso.onView(ViewMatchers.withText("Controlla la tua casella email per procedere con il cambio della password"))
            .inRoot(RootMatchers.isDialog())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}