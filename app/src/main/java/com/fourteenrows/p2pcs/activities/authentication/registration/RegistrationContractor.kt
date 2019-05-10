package com.fourteenrows.p2pcs.activities.authentication.registration

import com.fourteenrows.p2pcs.generalview.IView

interface RegistrationContractor {

    interface View : IView {
        fun makeEditTextDialog()
        fun updateViewData()
        fun makeAlertDialog(message: Int, title: Int)
    }

    interface Presenter {
        fun checkLoginValues(email: String, name: String, surname: String, pwd: String, pwd2: String)
        fun checkResetValues(email: String)
    }

    interface Interactor {
        fun checkStringLength(value: String, length: Int): Boolean
        fun checkStringsEqual(value1: String, value2: String): Boolean
        fun checkValueIsEmail(email: String): Boolean
        fun checkValueIsEmpty(value: String): Boolean
        fun insertUser(email: String, name: String, surname: String, pwd: String)
        fun sendResetEmail(email: String)
    }

    interface CompleteListener {
        fun onSuccess()
        fun onFailure()
    }
}