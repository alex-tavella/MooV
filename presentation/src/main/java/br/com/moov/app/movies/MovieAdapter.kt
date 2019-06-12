package br.com.moov.app.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.moov.app.R
import br.com.moov.domain.movie.Movie
import com.bumptech.glide.Glide

typealias OnMovieClick = (View, Int?) -> Unit
typealias OnMovieFavoriteChanged = (Boolean, Movie) -> Unit

class MovieAdapter(
    private val movieClickDelegate: OnMovieClick,
    private val movieFavoriteDelegate: OnMovieFavoriteChanged)
  : RecyclerView.Adapter<MovieViewHolder>() {

  private val movies = mutableListOf<Movie>()

  fun setItems(items: List<Movie>) {
    val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
      override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return movies[oldItemPosition].id == items[newItemPosition].id
      }

      override fun getOldListSize(): Int {
        return movies.size
      }

      override fun getNewListSize(): Int {
        return items.size
      }

      override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie = movies[oldItemPosition]
        val newMovie = items[newItemPosition]
        return oldMovie.isBookmarked == newMovie.isBookmarked
      }

    })

    movies.clear()
    movies.addAll(items)
    diffResult.dispatchUpdatesTo(this)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return MovieViewHolder(inflater.inflate(R.layout.item_movie, parent, false),
        movieClickDelegate, movieFavoriteDelegate)
  }

  override fun getItemCount(): Int {
    return movies.size
  }

  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    holder.setItem(movies[position])
  }
}

class MovieViewHolder(
    itemView: View,
    private val movieClickDelegate: OnMovieClick,
    private val onMovieFavoriteChange: OnMovieFavoriteChanged) : RecyclerView.ViewHolder(itemView) {

  private val posterView by lazy { itemView.findViewById<ImageView>(R.id.iv_movie_poster) }
  private val favoriteView by lazy { itemView.findViewById<CheckBox>(R.id.ib_movie_bookmark) }

  fun setItem(item: Movie) {
    posterView.apply {
      contentDescription = item.title
      Glide.with(this).load(item.thumbnailUrl).into(this)
      setOnClickListener { movieClickDelegate(this, item.id) }
    }
    favoriteView.apply {
      isChecked = item.isBookmarked
      setOnClickListener { onMovieFavoriteChange(isChecked, item) }
    }
  }
}
