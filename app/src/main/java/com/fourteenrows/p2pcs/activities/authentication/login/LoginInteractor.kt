package com.fourteenrows.p2pcs.activities.authentication.login

import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.authentication.login.LoginContractor.CompleteListener
import com.fourteenrows.p2pcs.activities.authentication.login.LoginContractor.Interactor
import com.fourteenrows.p2pcs.model.ModelFirebase
import com.fourteenrows.p2pcs.model.ModelValidator

class LoginInteractor(private val listener: CompleteListener) : Interactor {

    override fun authenticateUser(email: String, pwd: String) {
        ModelFirebase.authenticateUser(email, pwd)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (ModelFirebase.isEmailVerified()) {
                        listener.onSuccess()
                    } else {
                        ModelFirebase.sendVerificationEmail()
                        ModelFirebase.signOut()
                        listener.onFailure(R.string.not_verified, R.string.error)
                    }
                } else {
                    listener.onFailure(R.string.wrong_credentials, R.string.error)
                }
            }
    }

    override fun sendResetEmail(email: String) {
        ModelFirebase.sendResetEmail(email)
    }

    override fun checkValueIsEmail(email: String): Boolean = ModelValidator.checkValueIsEmail(email)

    override fun checkValueIsEmpty(email: String): Boolean = ModelValidator.checkValueIsEmpty(email)
}