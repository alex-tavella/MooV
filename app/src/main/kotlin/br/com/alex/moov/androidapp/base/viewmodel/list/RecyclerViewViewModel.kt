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

package br.com.alex.moov.androidapp.base.viewmodel.list

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutManager
import br.com.alex.moov.androidapp.base.viewmodel.ViewModel


abstract class RecyclerViewViewModel(savedInstanceState: State?) : ViewModel(savedInstanceState) {

  internal val layoutManager: LayoutManager by lazy { createLayoutManager() }
  private var savedLayoutManagerState: Parcelable? = null

  protected abstract fun getRecyclerViewViewModelAdapter(): RecyclerViewViewModelAdapter<*, *>
  protected abstract fun createLayoutManager(): LayoutManager

  init {
    if (savedInstanceState is RecyclerViewViewModelState) {
      savedLayoutManagerState = savedInstanceState.layoutManagerState
    }
  }

  override fun getInstanceState(): RecyclerViewViewModelState {
    return RecyclerViewViewModelState(this)
  }

  fun setupRecyclerView(recyclerView: RecyclerView) {
    if (savedLayoutManagerState != null) {
      layoutManager.onRestoreInstanceState(savedLayoutManagerState)
      savedLayoutManagerState = null
    }
    recyclerView.adapter = getRecyclerViewViewModelAdapter()
    recyclerView.layoutManager = layoutManager
  }

  open class RecyclerViewViewModelState : State {

    companion object {

      var CREATOR: Creator<RecyclerViewViewModelState> = object : Creator<RecyclerViewViewModelState> {
        override fun createFromParcel(source: Parcel): RecyclerViewViewModelState {
          return RecyclerViewViewModelState(source)
        }

        override fun newArray(size: Int): Array<RecyclerViewViewModelState?> {
          return arrayOfNulls(size)
        }
      }
    }

    val layoutManagerState: Parcelable

    constructor(viewModel: RecyclerViewViewModel) : super(viewModel) {
      layoutManagerState = viewModel.layoutManager.onSaveInstanceState()!!
    }

    constructor(`in`: Parcel) : super(`in`) {
      layoutManagerState = `in`.readParcelable<Parcelable>(
          LayoutManager::class.java.classLoader)
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
      super.writeToParcel(dest, flags)
      dest!!.writeParcelable(layoutManagerState, flags)
    }
  }
}