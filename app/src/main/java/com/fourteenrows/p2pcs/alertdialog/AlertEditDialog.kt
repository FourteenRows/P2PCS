package com.fourteenrows.p2pcs.alertdialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.fourteenrows.p2pcs.R

class AlertEditDialog private constructor(
    layout: LayoutInflater?,
    view: View?,
    context: Context?,
    title: String?,
    hint: String?,
    type: Int?,
    positive: String?
) {

    init {
        @SuppressLint("InflateParams")
        val dialogView = layout!!.inflate(R.layout.dialog_edit_text, null)
        val textField = view!!.findViewById(R.id.alertTextField) as EditText
        textField.inputType = type as Int
        textField.hint = hint
        val builder = AlertDialog.Builder(context as Context)
            .setTitle(title)
            .setView(dialogView)
            .setPositiveButton(positive) { _, _ ->

            }
            .setNeutralButton(R.string.cancel) { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    data class Builder(
        var layout: LayoutInflater? = null,
        var view: View? = null,
        var context: Context? = null,
        var title: String? = null,
        var hint: String? = null,
        var type: Int? = null,
        var positive: String? = null
    ) {

        fun layout(toLayout: LayoutInflater) = apply { layout = toLayout }
        fun view(toView: View) = apply { view = toView }
        fun context(toContext: Context) = apply { context = toContext }
        fun title(toTitle: String) = apply { title = toTitle }
        fun hint(toHint: String) = apply { hint = toHint }
        fun type(toType: Int) = apply { type = toType }
        fun positive(toPositive: String) = apply { positive = toPositive }
        fun build() = AlertEditDialog(layout, view, context, title, hint, type, positive)

    }
}