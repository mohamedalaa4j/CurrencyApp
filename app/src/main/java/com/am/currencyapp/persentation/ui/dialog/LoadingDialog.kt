package com.am.currencyapp.persentation.ui.dialog

import android.app.Activity
import android.app.AlertDialog
import android.view.View
import com.am.currencyapp.R

object LoadingDialog {
    private lateinit var dialog: AlertDialog

    fun init(activity: Activity) {
        dialog = AlertDialog.Builder(activity, R.style.DialogStyle)
            .setView(View.inflate(activity, R.layout.dialog_loading, null))
            .setCancelable(false)
            .create()
    }

    fun showDialog() {
        if (!dialog.isShowing) dialog.show()
    }

    fun dismissDialog() {
        if (dialog.isShowing) dialog.dismiss()
    }
}