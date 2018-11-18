package br.com.moov.app.util

import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import br.com.moov.app.R.string

typealias OkFunc = (DialogInterface, Int) -> Unit

object DialogFactory {

  fun createErrorDialog(activity: Activity, errorMessage: String, onOkAction: OkFunc): AlertDialog {
    return AlertDialog.Builder(activity)
        .setTitle(activity.getString(string.dialog_title_error))
        .setMessage(errorMessage)
        .setPositiveButton(android.R.string.ok, onOkAction)
        .create()
  }
}