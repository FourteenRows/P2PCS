package com.fourteenrows.p2pcs.activities.authentication.registration

import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.authentication.registration.RegistrationContractor.*

class RegistrationPresenter(toView: View) : Presenter, CompleteListener {

    private var view = toView
    private val interactor = RegistrationInteractor(this)

    init {
        view.initView()
    }

    override fun checkLoginValues(email: String, name: String, surname: String, pwd: String, pwd2: String) {
        if (!interactor.checkValueIsEmpty(email) || !interactor.checkValueIsEmpty(name)
            || !interactor.checkValueIsEmpty(surname) || !interactor.checkValueIsEmpty(pwd)
            || !interactor.checkValueIsEmpty(pwd2)
        ) {
            view.makeAlertDialog(R.string.all_fields_required, R.string.error)
            return
        }

        if (!interactor.checkValueIsEmail(email)) {
            view.makeAlertDialog(R.string.email_not_email, R.string.error)
            return
        }

        if (!interactor.checkStringLength(pwd, 6)) {
            view.makeAlertDialog(R.string.password_length, R.string.error)
            return
        }

        if (!interactor.checkStringsEqual(pwd, pwd2)) {
            view.makeAlertDialog(R.string.passwords_not_matching, R.string.error)
            return
        }

        interactor.insertUser(email, name, surname, pwd)
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
        view.makeAlertDialog(R.string.registration_successful, R.string.success)
    }

    override fun onFailure() {
        view.makeAlertDialog(R.string.email_already_in, R.string.error)
    }
}