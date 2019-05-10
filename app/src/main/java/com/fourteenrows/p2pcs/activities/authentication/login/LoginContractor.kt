package com.fourteenrows.p2pcs.activities.authentication.login

import com.fourteenrows.p2pcs.generalview.IView

interface LoginContractor {

    interface View : IView {
        fun makeEditTextDialog()
        fun updateViewData()
        fun makeAlertDialog(message: Int, title: Int)
    }

    interface Presenter {
        fun checkLoginValues(email: String, pwd: String)
        fun checkResetValues(email: String)
    }

    interface Interactor {
        fun checkValueIsEmpty(email: String): Boolean
        fun checkValueIsEmail(email: String): Boolean
        fun authenticateUser(email: String, pwd: String)
        fun sendResetEmail(email: String)

    }

    interface CompleteListener {
        fun onSuccess()
        fun onFailure(message: Int, title: Int)
    }
}