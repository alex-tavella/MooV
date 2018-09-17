package br.com.moov.app.util

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnScrollListener
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.delay

inline fun RecyclerView.onListEndReached(
    coroutineScope: CoroutineScope,
    crossinline block: () -> Unit) {

  val channel by lazy {
    coroutineScope.actor<Unit> {
      for (event in channel) {
        block()
        delay(300)
      }
    }
  }

  layoutManager?.let {
    it as? LinearLayoutManager
  }?.run {
    addOnScrollListener(object : OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (itemCount - findLastVisibleItemPosition() <= 3) {
          channel.offer(Unit)
        }
      }
    })
  } ?: logw { "This RecyclerView's layout manager is not a subclass of LinearLayoutManager" }
}