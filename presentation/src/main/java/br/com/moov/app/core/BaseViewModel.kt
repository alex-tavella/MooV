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
package br.com.moov.app.core

import androidx.lifecycle.ViewModel
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Suppress("EXPERIMENTAL_API_USAGE")
abstract class BaseViewModel<in I : UiEvent, O : UiModel> : ViewModel(), CoroutineScope {

    private val job: Job = Job()

    override var coroutineContext: CoroutineContext = job + Dispatchers.IO
        set(value) {
            field = job + value
        }

    private val uiEventChannel: SendChannel<I> = Channel<I>(capacity = Channel.UNLIMITED)
        .also { channel ->
            launch(coroutineContext) {
                for (action in channel) {
                    if (isActive) {
                        processUiEvent(action)
                    }
                }
            }
        }

    private val uiModelChannel: Channel<O> = Channel(Channel.CONFLATED)

    fun observe(coroutineScope: CoroutineScope, block: (O) -> Unit) {
        launch(coroutineScope.coroutineContext) {
            for (result in uiModelChannel) {
                if (isActive) {
                    block(result)
                }
            }
        }
    }

    protected abstract suspend fun processUiEvent(uiEvent: I)

    fun uiEvent(uiEvent: I) {
        uiEventChannel.offer(uiEvent)
    }

    protected fun emitUiModel(uiModel: O) {
        uiModelChannel.takeUnless { it.isClosedForSend }?.offer(uiModel)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
