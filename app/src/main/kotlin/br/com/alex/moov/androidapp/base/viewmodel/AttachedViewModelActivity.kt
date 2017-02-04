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

package br.com.alex.moov.androidapp.base.viewmodel

import android.app.Activity
import android.content.Intent
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import br.com.alex.moov.androidapp.base.AttachedActivity
import java.lang.ref.WeakReference


class AttachedViewModelActivity internal constructor(
    activity: AppCompatActivity) : AttachedActivity {
  private val weakActivity: WeakReference<AppCompatActivity> = WeakReference(activity)

  override fun startActivity(activityClass: Class<out Activity>) {
    val activity = weakActivity.get()
    if (activity != null && !activity.isFinishing) {
      activity.startActivity(Intent(activity, activityClass))
    }
  }

  override fun showDialog(dialogFragment: DialogFragment, tag: String) {
    val activity = weakActivity.get()
    if (activity != null && !activity.isFinishing) {
      dialogFragment.show(activity.supportFragmentManager, tag)
    }
  }
}
