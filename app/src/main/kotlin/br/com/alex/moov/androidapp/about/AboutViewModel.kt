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

import android.content.Context
import android.databinding.Bindable
import android.view.View
import br.com.alex.moov.BuildConfig
import br.com.alex.moov.R
import br.com.alex.moov.androidapp.base.AttachedActivity
import br.com.alex.moov.androidapp.base.viewmodel.ViewModel

class AboutViewModel(val context: Context, val attachedActivity: AttachedActivity) : ViewModel(
    null) {

  @Bindable fun getVersionName(): String = context.getString(R.string.app_version_name,
      BuildConfig.VERSION_NAME)

  @Bindable fun getAppName(): String = context.getString(R.string.app_name)

  fun onLicenseButtonClick(view: View) {
    attachedActivity.showDialog(LegalDialogFragment(), "license_dialog")
  }
}