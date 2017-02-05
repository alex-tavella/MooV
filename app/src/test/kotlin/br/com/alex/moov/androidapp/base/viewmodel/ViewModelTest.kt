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

import android.databinding.Observable
import br.com.alex.moov.androidapp.BaseApplicationTest
import br.com.alex.moov.androidapp.base.viewmodel.ViewModel.State
import org.mockito.Matchers.eq
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.verification.VerificationMode


abstract class ViewModelTest<ViewModelT : ViewModel> : BaseApplicationTest() {

  protected lateinit var viewModel: ViewModelT

  protected var savedInstanceState: State? = null

  protected lateinit var onPropertyChangedCallback: Observable.OnPropertyChangedCallback

  protected abstract fun createViewModel(): ViewModelT

  override fun setUp() {
    super.setUp()
    onPropertyChangedCallback = mock(Observable.OnPropertyChangedCallback::class.java)
    viewModel = createViewModel()
    viewModel.addOnPropertyChangedCallback(onPropertyChangedCallback)
  }

  protected fun verifyChanged() {
    verify(onPropertyChangedCallback)
        .onPropertyChanged(any(Observable::class.java), eq(0))
  }

  protected fun verifyPropertyChanged(propertyId: Int) {
    verify(onPropertyChangedCallback)
        .onPropertyChanged(any(Observable::class.java), eq(propertyId))
  }

  protected fun verifyPropertyChanged(propertyId: Int, verificationMode: VerificationMode) {
    verify(onPropertyChangedCallback, verificationMode)
        .onPropertyChanged(any(Observable::class.java), eq(propertyId))
  }

  protected fun rotateDestroy() {
    savedInstanceState = viewModel.getInstanceState()
    viewModel.removeOnPropertyChangedCallback(onPropertyChangedCallback)
    viewModel.onStop()
  }

  protected fun rotateCreate() {
    onPropertyChangedCallback = mock(Observable.OnPropertyChangedCallback::class.java)
    viewModel = createViewModel()
    viewModel.onRestoreState(savedInstanceState)
    viewModel.addOnPropertyChangedCallback(onPropertyChangedCallback)
    viewModel.onStart()
    savedInstanceState = null
  }
}