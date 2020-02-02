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

import android.support.v4.app.DialogFragment
import br.com.alex.moov.androidapp.base.viewmodel.ViewModelTest
import com.nhaarman.mockito_kotlin.capture
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Matchers.anyInt
import org.mockito.Matchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AboutViewModelTest : ViewModelTest<AboutViewModel>() {

  private val dialogCaptor: ArgumentCaptor<DialogFragment> = ArgumentCaptor.forClass(
      DialogFragment::class.java)

  @Captor private lateinit var tagCaptor: ArgumentCaptor<String>

  @Before override fun setUp() {
    super.setUp()

    `when`(appContext.getString(anyInt(), anyString())).thenReturn("Version 1.0.0")
    `when`(appContext.getString(anyInt())).thenReturn("MooV")
  }

  override fun createViewModel() = AboutViewModel(appContext, attachedActivity)

  @Test fun testVersionName() {
    // Given
    val versionName = viewModel.getVersionName()

    // Then
    assertEquals("Version 1.0.0", versionName)
  }

  @Test fun testAppName() {
    // Given
    val appName = viewModel.getAppName()

    // Then
    assertEquals("MooV", appName)
  }

  @Test fun testLicenseButtonClick() {

    // When
    viewModel.onLicenseButtonClick()

    // Then
    verify(attachedActivity).showDialog(capture(dialogCaptor), capture(tagCaptor))
    assertTrue(dialogCaptor.value is LegalDialogFragment)
    assertEquals("license_dialog", tagCaptor.value)
  }

  @Test fun testRotate() {

    // When
    rotateDestroy()
    rotateCreate()
    viewModel.onLicenseButtonClick()

    // Then
    assertEquals("Version 1.0.0", viewModel.getVersionName())
    assertEquals("MooV", viewModel.getAppName())
    verify(attachedActivity).showDialog(capture(dialogCaptor), capture(tagCaptor))
    assertTrue(dialogCaptor.value is LegalDialogFragment)
    assertEquals("license_dialog", tagCaptor.value)
  }
}