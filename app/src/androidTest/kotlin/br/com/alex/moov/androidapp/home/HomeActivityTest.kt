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

package br.com.alex.moov.androidapp.home

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import br.com.alex.moov.R
import br.com.alex.moov.androidapp.about.AboutActivity
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

  @get:Rule var mActivityTestRule: ActivityTestRule<HomeActivity> = IntentsTestRule(
      HomeActivity::class.java, true, false)

  @Test
  fun testOnCreate() {
    mActivityTestRule.launchActivity(null)

    val activity = mActivityTestRule.activity
    Assert.assertNotNull(activity.emailSender)
    Assert.assertNotNull(activity.eventLogger)
    Assert.assertNotNull(activity.screenSwitcher)
    Assert.assertNotNull(activity.mBinding)
    Assert.assertNotNull(activity.mBinding.recyclerView)
    Assert.assertNotNull(activity.mBinding.viewModel)
  }

  @Test fun testGoToAbout() {
    mActivityTestRule.launchActivity(null)

    openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext())

    onView(withText(R.string.menu_about))
        .perform(click())

    intended(hasComponent(hasClassName(AboutActivity::class.java.name)))
  }

  @Test fun testSendFeedback() {
    mActivityTestRule.launchActivity(null)

    openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext())
    onView(withText(R.string.menu_feedback))
        .perform(click())

    intended(allOf(hasAction(Intent.ACTION_CHOOSER),
        hasExtra(`is`(Intent.EXTRA_INTENT),
            allOf(hasAction(Intent.ACTION_SENDTO),
                hasExtra(Intent.EXTRA_SUBJECT, "[MooV - Feedback]")))))
  }

}