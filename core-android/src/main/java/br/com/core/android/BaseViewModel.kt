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
package br.com.core.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

abstract class BaseViewModel<in I : UiEvent, O : UiModel> : ViewModel() {

    private val uiEventChannel: SendChannel<I> = Channel<I>(capacity = Channel.UNLIMITED)
        .also { channel ->
            viewModelScope.launch {
                for (action in channel) {
                    if (isActive) {
                        processUiEvent(action)
                    }
                }
            }
        }

    private val uiModelChannel: Channel<O> = Channel(Channel.CONFLATED)

    fun observe(coroutineScope: CoroutineScope, block: (O) -> Unit) {
        viewModelScope.launch(coroutineScope.coroutineContext) {
            for (result in uiModelChannel) {
                if (isActive) {
                    block(result)
                }
            }
        }
    }

    protected abstract suspend fun processUiEvent(uiEvent: I)

    fun uiEvent(uiEvent: I) {
        uiEventChannel.trySend(uiEvent)
    }

    protected fun emitUiModel(uiModel: O) {
        uiModelChannel.takeUnless { it.isClosedForSend }?.trySend(uiModel)
    }
}
