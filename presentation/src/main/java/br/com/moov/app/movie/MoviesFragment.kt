package br.com.moov.app.movie

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import br.com.moov.app.R
import br.com.moov.app.core.BaseFragment
import br.com.moov.app.util.logd
import br.com.moov.app.util.onListEndReached
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment() {

  private val viewModel by viewModel<MoviesViewModel>()

  private val loadingProgressBar: ProgressBar by lazy {
    view!!.findViewById<ProgressBar>(R.id.progressBar)
  }

  private val recyclerView by lazy { view!!.findViewById<RecyclerView>(R.id.rv_movies) }

  private val adapter by lazy { MovieAdapter(context!!) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    logd { "Emitting enter screen ui event" }
    viewModel.uiEvent(EnterScreenUiEvent())
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_movies, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    recyclerView.adapter = adapter

    recyclerView.onListEndReached(this) {
      logd { "Emitting finished scroll ui event" }
      viewModel.uiEvent(FinishedScrollingUiEvent())
    }
  }

  override fun onStart() {
    super.onStart()
    viewModel.observe(this, ::renderUiModel)
  }

  private fun renderUiModel(uiModel: MoviesUiModel) {
    logd { "Rendering model $uiModel" }
    if (uiModel.loading) {
      loadingProgressBar.visibility = View.VISIBLE
    } else {
      loadingProgressBar.visibility = View.GONE
    }
    adapter.setItems(uiModel.movies)
  }
}