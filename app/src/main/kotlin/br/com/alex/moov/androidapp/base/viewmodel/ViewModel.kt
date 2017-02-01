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

import android.databinding.BaseObservable
import android.os.Parcel
import android.os.Parcelable


abstract class ViewModel(savedInstanceState: State?) : BaseObservable() {

  open fun onStart() {
  }

  open fun getInstanceState(): State {
    return State(this);
  }

  open fun onStop() {
  }

  open class State : Parcelable {
    companion object {
      @JvmField val CREATOR: Parcelable.Creator<State> = object : Parcelable.Creator<State> {
        override fun createFromParcel(source: Parcel): State = State(source)
        override fun newArray(size: Int): Array<State?> = arrayOfNulls(size)
      }
    }

    constructor(viewModel: ViewModel)

    constructor(source: Parcel)

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {}
  }
}