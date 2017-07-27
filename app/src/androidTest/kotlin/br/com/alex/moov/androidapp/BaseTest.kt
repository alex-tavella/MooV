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

package br.com.alex.moov.androidapp

import android.support.annotation.CallSuper
import android.support.test.InstrumentationRegistry
import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.alex.moov.androidapp.base.viewmodel.list.RecyclerViewViewModelAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before


open class BaseTest {

  @CallSuper
  @Before
  @Throws(Exception::class)
  fun setUp() {
    val instrumentation = InstrumentationRegistry.getInstrumentation()
    val application = instrumentation
        .targetContext.applicationContext as MooVApplication
  }

  companion object {

    fun withRecyclerViewPosition(position: Int): Matcher<View> {
      return object : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description) {
          description.appendText("with RecyclerView position: " + position)
        }

        override fun matchesSafely(view: View): Boolean {
          if (view.parent !is RecyclerView) {
            return false
          }

          val recyclerView = view.parent as RecyclerView
          recyclerView.adapter as? RecyclerViewViewModelAdapter<*, *> ?: return false
          return position == recyclerView.getChildAdapterPosition(view)
        }
      }
    }
  }
}