package br.com.moov.app.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<O : UiModel> : ViewModel(), CoroutineScope {

  private val job: Job = Job()

  override var coroutineContext: CoroutineContext = job + Dispatchers.IO
    set(value) {
      field = job + value
    }

  private val uiEventChannel: SendChannel<UiEvent> = Channel<UiEvent>(capacity = Channel.UNLIMITED)
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

  protected abstract suspend fun processUiEvent(uiEvent: UiEvent)

  fun uiEvent(uiEvent: UiEvent) {
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