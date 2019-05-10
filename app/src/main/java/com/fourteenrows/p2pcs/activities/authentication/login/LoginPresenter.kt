package com.fourteenrows.p2pcs.activities.authentication.login

import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.authentication.login.LoginContractor.*

class LoginPresenter(toView: View) : Presenter, CompleteListener {

    private var view = toView
    private var interactor: Interactor = LoginInteractor(this)

    init {
        view.initView()
    }

    override fun checkLoginValues(email: String, pwd: String) {
        if (!interactor.checkValueIsEmpty(email) || !interactor.checkValueIsEmpty(pwd)) {
            view.makeAlertDialog(R.string.all_fields_required, R.string.error)
            return
        }

        if (!interactor.checkValueIsEmail(email)) {
            view.makeAlertDialog(R.string.email_not_email, R.string.error)
            return
        }

        interactor.authenticateUser(email, pwd)
    }

    override fun checkResetValues(email: String) {
        if (!interactor.checkValueIsEmpty(email)) {
            view.makeAlertDialog(R.string.field_required, R.string.error)
            return
        }

        if (!interactor.checkValueIsEmail(email)) {
            view.makeAlertDialog(R.string.email_not_email, R.string.error)
            return
        }

        interactor.sendResetEmail(email)
        view.makeAlertDialog(R.string.reset_email_sent, R.string.success)
    }


    override fun onSuccess() {
        view.updateViewData()
    }

    override fun onFailure(message: Int, title: Int) {
        view.makeAlertDialog(message, title)
    }
}