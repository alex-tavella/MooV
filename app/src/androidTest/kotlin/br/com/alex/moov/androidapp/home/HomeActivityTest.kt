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

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withParent
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import br.com.alex.moov.R
import junit.framework.Assert
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

  @get:Rule var mActivityTestRule: ActivityTestRule<HomeActivity> = ActivityTestRule(
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

  @Test fun testGoToMovies() {
    mActivityTestRule.launchActivity(null)

    val appCompatImageButton = onView(
        allOf<View>(withContentDescription("Open navigation drawer"),
            withParent(withId(R.id.toolbar)),
            isDisplayed()))
    appCompatImageButton.perform(click())

    val appCompatCheckedTextView = onView(
        allOf<View>(withId(R.id.design_menu_item_text), withText("Movies"), isDisplayed()))
    appCompatCheckedTextView.perform(click())
  }

  @Test fun testGoToTvShows() {
    mActivityTestRule.launchActivity(null)

    val appCompatImageButton = onView(
        allOf<View>(withContentDescription("Open navigation drawer"),
            withParent(withId(R.id.toolbar)),
            isDisplayed()))
    appCompatImageButton.perform(click())

    val appCompatCheckedTextView = onView(
        allOf<View>(withId(R.id.design_menu_item_text), withText("Tv Shows"), isDisplayed()))
    appCompatCheckedTextView.perform(click())
  }

  @Test fun testGoToAbout() {
    mActivityTestRule.launchActivity(null)

    val appCompatImageButton = onView(
        allOf<View>(withContentDescription("Open navigation drawer"),
            withParent(withId(R.id.toolbar)),
            isDisplayed()))
    appCompatImageButton.perform(click())

    val appCompatCheckedTextView = onView(
        allOf<View>(withId(R.id.design_menu_item_text), withText("About"), isDisplayed()))
    appCompatCheckedTextView.perform(click())
  }

  @Test fun testSendFeedback() {
    mActivityTestRule.launchActivity(null)

    val appCompatImageButton = onView(
        allOf<View>(withContentDescription("Open navigation drawer"),
            withParent(withId(R.id.toolbar)),
            isDisplayed()))
    appCompatImageButton.perform(click())

    val appCompatCheckedTextView = onView(
        allOf<View>(withId(R.id.design_menu_item_text), withText("Send feedback"), isDisplayed()))
    appCompatCheckedTextView.perform(click())
  }

}