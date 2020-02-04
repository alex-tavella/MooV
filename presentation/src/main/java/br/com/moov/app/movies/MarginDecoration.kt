package br.com.moov.app.movies

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.moov.app.R

class MarginDecoration(context: Context) : RecyclerView.ItemDecoration() {
  private val margin: Int = context.resources.getDimensionPixelSize(R.dimen.grid_item_margin)

  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {
    outRect.set(margin, margin, margin, margin)
  }
}
