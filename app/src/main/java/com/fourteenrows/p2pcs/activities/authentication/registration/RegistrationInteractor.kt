package com.fourteenrows.p2pcs.activities.authentication.registration

import com.fourteenrows.p2pcs.activities.authentication.registration.RegistrationContractor.CompleteListener
import com.fourteenrows.p2pcs.activities.authentication.registration.RegistrationContractor.Interactor
import com.fourteenrows.p2pcs.model.ModelFirebase
import com.fourteenrows.p2pcs.model.ModelValidator

class RegistrationInteractor(private val listener: CompleteListener) : Interactor {

    override fun insertUser(email: String, name: String, surname: String, pwd: String) {
        ModelFirebase.checkNewEmail(email)
            .addOnCompleteListener { methods ->
                if (methods.isSuccessful) {
                    if (methods.result!!.signInMethods!!.size == 0) {
                        ModelFirebase.insertUser(email, pwd)
                            .addOnSuccessListener {
                                addUserData(ModelFirebase.getUid(), name, surname, email)
                                listener.onSuccess()
                            }
                    } else {
                        listener.onFailure()
                    }
                }
            }
    }

    private fun addUserData(uid: String?, name: String, surname: String, email: String) {
        ModelFirebase.addUserData(uid, name, surname, email)
            .addOnSuccessListener {
                ModelFirebase.sendVerificationEmail()
            }
    }

    override fun sendResetEmail(email: String) {
        ModelFirebase.sendResetEmail(email)
    }

    override fun checkValueIsEmpty(value: String): Boolean = ModelValidator.checkValueIsEmpty(value)

    override fun checkStringLength(value: String, length: Int): Boolean =
        ModelValidator.checkStringLength(value, length)

    override fun checkStringsEqual(value1: String, value2: String): Boolean =
        ModelValidator.checkStringsEqual(value1, value2)

    override fun checkValueIsEmail(email: String): Boolean = ModelValidator.checkValueIsEmail(email)
}