/*
 * Copyright 2020 Alex Almeida Tavella
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.core.android.views

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import br.com.core.android.logw
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

inline fun RecyclerView.onEndReached(
    coroutineScope: CoroutineScope,
    crossinline block: () -> Unit
) {

    val channel by lazy {
        Channel<Unit>(Channel.RENDEZVOUS).also { channel ->
            coroutineScope.launch {
                for (event in channel) {
                    block()
                    delay(500)
                }
            }
        }
    }

    layoutManager?.let {
        it as? LinearLayoutManager
    }?.run {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (itemCount - findLastVisibleItemPosition() <= 3 && dy > 20 && itemCount > 0) {
                    channel.trySend(Unit)
                }
            }
        })
    } ?: logw { "This RecyclerView's layout manager is not a subclass of LinearLayoutManager" }
}
