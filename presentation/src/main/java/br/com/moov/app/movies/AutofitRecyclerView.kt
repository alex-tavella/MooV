package br.com.moov.app.movies

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AutofitRecyclerView : RecyclerView {

  private lateinit var manager: GridLayoutManager
  private var columnWidth = -1

  constructor(context: Context) : super(context) {
    init(context)
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    init(context, attrs)
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs,
      defStyleAttr) {
    init(context, attrs)
  }

  private fun init(context: Context, attrs: AttributeSet? = null) {
    if (attrs != null) {
      val attrsArray = intArrayOf(android.R.attr.columnWidth)
      val ta = context.obtainStyledAttributes(attrs, attrsArray)
      columnWidth = ta.getDimensionPixelSize(0, -1)
      ta.recycle()
    }

    manager = GridLayoutManager(context, 1)
    layoutManager = manager
  }

  override fun onMeasure(widthSpec: Int, heightSpec: Int) {
    super.onMeasure(widthSpec, heightSpec)
    if (columnWidth > 0) {
      val spanCount = Math.max(1, measuredWidth / columnWidth)
      manager.spanCount = spanCount
    }
  }
}
