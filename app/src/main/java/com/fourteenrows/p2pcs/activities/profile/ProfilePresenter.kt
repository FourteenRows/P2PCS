package com.fourteenrows.p2pcs.activities.profile

import android.app.Activity
import android.content.SharedPreferences
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.profile.ProfileContractor.*
import com.fourteenrows.p2pcs.model.User

class ProfilePresenter(toView: View) : Presenter, CompleteListener {

    private var view = toView
    private val interactor = ProfileInteractor(this)

    init {
        view.initView()
        loadUserData()
    }

    //TODO("MOVE CACHE AWAY")

    override fun loadUserData() {
        val activity = view as Activity
        val cache: SharedPreferences = activity.getSharedPreferences("userData", 0)
        if (!cache.contains("mail")) {
            interactor.loadUser()
        } else {
            val user = User(
                cache.getString("name", "")!!,
                cache.getString("surname", "")!!,
                cache.getString("mail", "")!!,
                cache.getLong("exp", 0),
                cache.getLong("gaia_coins", 0),
                cache.getLong("week_points", 0)
            )
            getSuccessful(user)
        }
    }

    override fun getSuccessful(user: User) {
        view.replaceData(user)
    }

    override fun sendReset() {
        interactor.sendResetEmailKnown()
    }

    override fun updateData(field: String, input: String) {
        if (!interactor.checkValueIsEmpty(input)) {
            view.makeAlertDialog(R.string.field_required, R.string.error)
            return
        }

        interactor.updateData(field, input)
        val activity = view as Activity
        val cache: SharedPreferences = activity.getSharedPreferences("userData", 0)
        if (cache.contains("mail")) {
            val editor: SharedPreferences.Editor = cache.edit()
            editor.putString(field, input)
            editor.apply()
        }
        view.refresh()
    }

    override fun onSuccess(message: Int, title: Int) {
        view.makeAlertDialog(message, title)
    }
}