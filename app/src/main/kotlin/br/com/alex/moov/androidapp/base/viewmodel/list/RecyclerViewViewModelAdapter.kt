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
import android.support.annotation.LayoutRes
import android.support.annotation.VisibleForTesting
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alex.moov.androidapp.base.viewmodel.list.RecyclerViewViewModelAdapter.ItemViewHolder

abstract class RecyclerViewViewModelAdapter<ITEM_T, VIEW_MODEL_T : ItemViewModel<ITEM_T>>(
    protected val context: Context) :
    RecyclerView.Adapter<ItemViewHolder<ITEM_T, VIEW_MODEL_T>>() {

  val items: MutableList<ITEM_T> = arrayListOf()

  protected val inflater: LayoutInflater by lazy {
    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
  }

  abstract protected @LayoutRes fun getLayoutRes(): Int

  abstract protected fun createItemViewModel(): VIEW_MODEL_T

  abstract protected fun createBindingAndSetupViewModel(view: View,
      itemViewModel: VIEW_MODEL_T): ViewDataBinding

  override fun onCreateViewHolder(parent: ViewGroup?,
      viewType: Int): ItemViewHolder<ITEM_T, VIEW_MODEL_T> {
    val view = inflater.inflate(getLayoutRes(), parent, false)

    val viewModel = createItemViewModel()
    val binding = createBindingAndSetupViewModel(view, viewModel)

    return ItemViewHolder(view, binding, viewModel)
  }

  override fun onBindViewHolder(holder: ItemViewHolder<ITEM_T, VIEW_MODEL_T>, position: Int) {
    holder.setItem(items[(position)])
  }

  override fun getItemCount(): Int {
    return items.count()
  }

  fun clear() {
    items.clear()
    notifyDataSetChange()
  }

  fun addAll(newItems: List<ITEM_T>) {
    items.addAll(newItems)
    notifyDataSetChange()
  }

  @VisibleForTesting
  open protected fun notifyDataSetChange() {
    notifyDataSetChanged()
  }

  open class ItemViewHolder<in T, out VT : ItemViewModel<T>>(itemView: View,
      private val binding: ViewDataBinding, protected val viewModel: VT) : RecyclerView.ViewHolder(
      itemView) {

    open internal fun setItem(item: T) {
      viewModel.setItem(item)
      binding.executePendingBindings()
    }
  }
}