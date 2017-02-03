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

package br.com.alex.moov.androidapp.email

import android.content.Context
import android.content.Intent
import android.net.Uri
import br.com.alex.moov.R.string

class IntentEmailSender(val context: Context): EmailSender {

  companion object {
    val RECIPIENT = "alexkotr@gmail.com"
    val SUBJECT = "[MooV - Feedback]"
  }

  override fun sendEmail(recipient: String, subject: String) {
    val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", recipient, null))
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    context.startActivity(
        Intent.createChooser(intent, context.getString(string.action_send_email))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
  }

  override fun sendFeedbackEmail() {
    sendEmail(RECIPIENT, SUBJECT)
  }
}