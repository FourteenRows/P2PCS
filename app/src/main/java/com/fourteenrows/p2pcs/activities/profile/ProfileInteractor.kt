package com.fourteenrows.p2pcs.activities.profile

import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.activities.profile.ProfileContractor.CompleteListener
import com.fourteenrows.p2pcs.activities.profile.ProfileContractor.Interactor
import com.fourteenrows.p2pcs.model.ModelFirebase
import com.fourteenrows.p2pcs.model.ModelValidator

class ProfileInteractor(private val listener: CompleteListener) : Interactor {

    override fun checkValueIsEmpty(input: String): Boolean = ModelValidator.checkValueIsEmpty(input)

    override fun loadUser() {
        ModelFirebase.getUserDocument()
            .addOnSuccessListener {
                val user = ModelFirebase.buildUser(it)
                listener.getSuccessful(user)
            }
    }

    override fun sendResetEmailKnown() {
        ModelFirebase.sendResetEmailKnown()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    listener.onSuccess(R.string.password_change, R.string.success)
                }
            }
    }

    override fun updateData(field: String, input: String) {
        ModelFirebase.updateField(field, input)
    }
}