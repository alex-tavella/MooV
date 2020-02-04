/*
 * Copyright 2020 Alex Almeida Tavella
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.moov.app.util

import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import br.com.moov.app.R.string

typealias OkFunc = (DialogInterface, Int) -> Unit

object DialogFactory {

    fun createErrorDialog(
        activity: Activity,
        errorMessage: String,
        onOkAction: OkFunc
    ): AlertDialog {
        return AlertDialog.Builder(activity)
            .setTitle(activity.getString(string.dialog_title_error))
            .setMessage(errorMessage)
            .setPositiveButton(android.R.string.ok, onOkAction)
            .create()
    }
}
