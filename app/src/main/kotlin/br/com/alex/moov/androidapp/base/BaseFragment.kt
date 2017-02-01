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

package br.com.alex.moov.androidapp.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import br.com.alex.moov.androidapp.ApplicationComponent
import br.com.alex.moov.androidapp.MooVApplication
import br.com.alex.moov.androidapp.base.viewmodel.ViewModel
import br.com.alex.moov.androidapp.base.viewmodel.ViewModel.State


abstract class BaseFragment : Fragment() {

  companion object {
    private val EXTRA_VIEW_MODEL_STATE = "viewModelState"
  }

  private var viewModel: ViewModel? = null

  protected abstract fun createAndBindViewModel(root: View, savedViewModelState: State?): ViewModel

  protected abstract fun injectDependencies(applicationComponent: ApplicationComponent)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState);

    injectDependencies((activity.applicationContext as MooVApplication).getAppComponent())

    var savedViewModelState: State? = null
    if (savedInstanceState != null) {
      savedViewModelState = savedInstanceState.getParcelable(EXTRA_VIEW_MODEL_STATE)
    }
    viewModel = createAndBindViewModel(view!!, savedViewModelState)
  }

  override fun onStart() {
    super.onStart()
    viewModel?.onStart()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putParcelable(EXTRA_VIEW_MODEL_STATE, viewModel?.getInstanceState())
  }

  override fun onStop() {
    super.onStop()
    viewModel?.onStop()
  }
}