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

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.alex.moov.androidapp.base.viewmodel.list.RecyclerViewViewModelAdapter.ItemViewHolder

abstract class RecyclerViewViewModelAdapter<ITEM_T, VIEW_MODEL_T : ItemViewModel<ITEM_T>>(
    protected val context: Context) :
    RecyclerView.Adapter<ItemViewHolder<ITEM_T, VIEW_MODEL_T>>() {

  val items: MutableList<ITEM_T> = arrayListOf()

  override fun onBindViewHolder(holder: ItemViewHolder<ITEM_T, VIEW_MODEL_T>, position: Int) {
    holder.setItem(items.get(position))
  }

  override fun getItemCount(): Int {
    return items.count()
  }

  open class ItemViewHolder<T, out VT : ItemViewModel<T>>(itemView: View,
      private val binding: ViewDataBinding, protected val viewModel: VT) : RecyclerView.ViewHolder(
      itemView) {

    open internal fun setItem(item: T) {
      viewModel.setItem(item)
      binding.executePendingBindings()
    }
  }
}