package com.fourteenrows.p2pcs.menu

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.widget.Toolbar
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.avatar.AvatarView
import com.fourteenrows.p2pcs.activities.booster.BoosterView
import com.fourteenrows.p2pcs.activities.car.CarView
import com.fourteenrows.p2pcs.activities.profile.ProfileView
import com.fourteenrows.p2pcs.activities.quest.QuestView
import com.fourteenrows.p2pcs.activities.reservation.ReservationView
import com.fourteenrows.p2pcs.activities.shop.ShopView
import com.fourteenrows.p2pcs.activities.trip.TripView
import com.fourteenrows.p2pcs.generalview.IView
import com.fourteenrows.p2pcs.model.ModelFirebase
import com.fourteenrows.p2pcs.model.User
import com.google.firebase.auth.FirebaseAuth
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import kotlinx.android.synthetic.main.app_bar.view.*

object DrawerUtil {

    @SuppressLint("CommitPrefEdits")
    fun getDrawer(activity: Activity, toolbar: Toolbar) {

        val items = mutableListOf<PrimaryDrawerItem>()
        items.add(generateItem("Prenotazioni", R.drawable.icon_reservations, activity, ReservationView())!!)
        items.add(generateItem("Veicoli", R.drawable.icon_cars, activity, CarView())!!)
        items.add(generateItem("Viaggi", R.drawable.icon_trips, activity, TripView())!!)
        items.add(generateItem("Shop", R.drawable.icon_shop, activity, ShopView())!!)
        items.add(generateItem("Profilo", R.drawable.icon_profile, activity, ProfileView())!!)
        items.add(generateItem("Missioni", R.drawable.icon_quests, activity, QuestView())!!)
        items.add(generateItem("Avatar", R.drawable.icon_avatar, activity, AvatarView())!!)
        items.add(generateItem("Boosters", R.drawable.icon_boosters, activity, BoosterView())!!)
        val logout = {
            activity.getSharedPreferences("userData", 0).edit().clear().apply()
            FirebaseAuth.getInstance().signOut()
        }
        items.add(generateItemWithFun("Logout", R.drawable.icon_logout, activity, ReservationView(), logout)!!)

        val cache: SharedPreferences = activity.getSharedPreferences("userData", 0)
        if (!cache.contains("mail")) {
            ModelFirebase.getUserDocument()
                .addOnSuccessListener {
                    val user: User = ModelFirebase.buildUser(it)

                    val editor: SharedPreferences.Editor = cache.edit()
                    editor.putString("name", user.name)
                    editor.putString("surname", user.surname)
                    editor.putString("mail", user.mail)
                    editor.putLong("exp", user.exp)
                    editor.putLong("gaia_coins", user.gaia_coins)
                    editor.putLong("week_points", user.week_points)
                    editor.apply()

                    toolbar.gaiaCoinValue.text = user.gaia_coins.toString()
                    toolbar.weekPointValue.text = user.week_points.toString()
                    generateDrawer(toolbar, activity, items, user.name, user.surname, user.mail)
                }
        } else {
            val user = User(
                cache.getString("name", "")!!,
                cache.getString("surname", "")!!,
                cache.getString("mail", "")!!,
                cache.getLong("exp", 0),
                cache.getLong("gaia_coins", 0),
                cache.getLong("week_points", 0)
            )
/*
            val toListen1 = "gaia_coins"
            val toListen2 = "week_points"

            val listener1 = SharedPreferences.OnSharedPreferenceChangeListener {cache, toListen1 ->
                Log.d("CACHEEEEEEEEEEEe", "VEZ I GA FATTO POCIO COI SCHEI ${cache.getString(toListen1, "")}")
            }

            val listener2 = SharedPreferences.OnSharedPreferenceChangeListener {cache, toListen2 ->
                Log.d("CACHEEEEEEEEEEEe", "VEZ I GA FATTO POCIO COI PUNTI ${cache.getString(toListen2, "")}")
            }


            cache.registerOnSharedPreferenceChangeListener(listener1)
            cache.registerOnSharedPreferenceChangeListener(listener2)
*/
            toolbar.gaiaCoinValue.text = user.gaia_coins.toString()
            toolbar.weekPointValue.text = user.week_points.toString()
            generateDrawer(toolbar, activity, items, user.name, user.surname, user.mail)
        }
    }

    private fun generateItemWithFun(
        name: String,
        icon: Int,
        activity: Activity,
        toActivity: IView,
        logout: () -> Unit
    ): PrimaryDrawerItem? {
        return PrimaryDrawerItem()
            .withIdentifier(1)
            .withName(name)
            .withIcon(icon)
            .withOnDrawerItemClickListener { view, _, _ ->
                logout()
                view.context.startActivity(Intent(activity, toActivity.javaClass))
                true
            }
    }

    private fun generateItem(name: String, icon: Int, activity: Activity, toActivity: IView): PrimaryDrawerItem? {
        return PrimaryDrawerItem()
            .withIdentifier(1)
            .withName(name)
            .withIcon(icon)
            .withOnDrawerItemClickListener { view, _, _ ->
                view.context.startActivity(Intent(activity, toActivity.javaClass))
                true
            }
    }

    private fun generateDrawer(
        toolbar: Toolbar,
        activity: Activity,
        items: MutableList<PrimaryDrawerItem>,
        name: String,
        surname: String,
        email: String
    ): Drawer? {
        val drawer = DrawerBuilder()
            .withActivity(activity)
            .withToolbar(toolbar)
            .withAccountHeader(generateAccountHeader(activity, "$name $surname", email)!!)
            .withActionBarDrawerToggle(true)
            .withActionBarDrawerToggleAnimated(true)
            .withCloseOnClick(true)
            .withSelectedItem(-1)
        for (item in items) {
            drawer.addDrawerItems(item)
        }
        return drawer.build()
    }

    private fun generateAccountHeader(activity: Activity, name: String, email: String): AccountHeader? {
        return AccountHeaderBuilder()
            .withActivity(activity)
            .addProfiles(generateProfile(name, email))
            .withSelectionListEnabled(false)
            .build()
    }

    private fun generateProfile(name: String, email: String): ProfileDrawerItem? {
        return ProfileDrawerItem()
            .withName(name)
            .withEmail(email)
            .withIcon(R.drawable.avatar) /*TODO("Set avatar image")*/
    }
}