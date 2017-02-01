/*
 *     Copyright 2017 Alex Almeida Tavella
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package br.com.alex.moov.androidapp.about

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.alex.moov.R


class LegalDialogFragment : DialogFragment() {

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

    val builder = AlertDialog.Builder(context)

    val licenseTextView = TextView(activity)
    licenseTextView.movementMethod = LinkMovementMethod.getInstance()

    if (android.os.Build.VERSION.SDK_INT >= 24) {
      licenseTextView.text = Html.fromHtml(getString(R.string.license_content),
          Html.FROM_HTML_MODE_COMPACT)
    } else {
      @Suppress("DEPRECATION")
      licenseTextView.text = Html.fromHtml(getString(R.string.license_content))
    }
    builder.setTitle(R.string.license_title)
        .setView(licenseTextView)
        .setNegativeButton(android.R.string.ok, null)

    return builder.create()
  }
}