package br.com.moov.app.core

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.SendChannel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.isActive
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

abstract class BaseViewModel<O : UiModel> : ViewModel(), CoroutineScope {

  private val job: Job = Job()

  override val coroutineContext: CoroutineContext
    get() = job + Dispatchers.IO

  private val uiEventChannel: SendChannel<UiEvent> = actor(capacity = Channel.UNLIMITED, block = {
    for (action in this) {
      if (isActive) {
        processUiEvent(action)
      }
    }
  })

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