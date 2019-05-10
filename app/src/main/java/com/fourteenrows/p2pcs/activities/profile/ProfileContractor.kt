package com.fourteenrows.p2pcs.activities.profile

import com.fourteenrows.p2pcs.generalview.IView
import com.fourteenrows.p2pcs.model.User

interface ProfileContractor {
    interface View : IView {
        fun makeEditTextDialog(
            hint: String,
            title: String,
            type: Int,
            positive: Int,
            placeholder: String,
            field: String
        )

        fun replaceData(user: User)
        fun makeAlertDialog(message: Int, title: Int)
        fun refresh()
    }

    interface Presenter {
        fun loadUserData()
        fun sendReset()
        fun updateData(field: String, input: String)
    }

    interface Interactor {
        fun loadUser()
        fun checkValueIsEmpty(input: String): Boolean
        fun sendResetEmailKnown()
        fun updateData(field: String, input: String)
    }

    interface CompleteListener {
        fun getSuccessful(user: User)
        fun onSuccess(message: Int, title: Int)
    }
}