package br.com.moov.app.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

inline fun RecyclerView.onEndReached(
    coroutineScope: CoroutineScope,
    crossinline block: () -> Unit) {

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
          channel.offer(Unit)
        }
      }
    })
  } ?: logw { "This RecyclerView's layout manager is not a subclass of LinearLayoutManager" }
}