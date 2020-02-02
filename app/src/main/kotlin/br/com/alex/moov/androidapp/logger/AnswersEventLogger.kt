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

package br.com.alex.moov.androidapp.logger

import android.view.MenuItem
import br.com.alex.moov.androidapp.logger.EventLogger.ContentType
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.crashlytics.android.answers.CustomEvent

class AnswersEventLogger : EventLogger {

  override fun logContentView(contentName: String, contentType: ContentType,
      contentId: String?) {
    val contentViewEvent = ContentViewEvent()

    if (contentId != null) {
      contentViewEvent.putContentId(contentId)
    }

    Answers.getInstance().logContentView(
        contentViewEvent
            .putContentType(contentType.name)
            .putContentName(contentName))
  }

  override fun logHomeNavigationDrawerEvent(item: MenuItem) {
    Answers.getInstance().logCustom(CustomEvent("Navigation")
        .putCustomAttribute("Context", "Home Drawer")
        .putCustomAttribute("item", item.toString()))
  }
}