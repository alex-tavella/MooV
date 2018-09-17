package br.com.moov.app.movie

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.moov.app.R
import br.com.moov.domain.movie.Movie

class MovieAdapter(context: Context) : RecyclerView.Adapter<MovieViewHolder>() {

  private val inflater = LayoutInflater.from(context)

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
        return movies[oldItemPosition].title == items[newItemPosition].title
      }

    })

    movies.clear()
    movies.addAll(items)
    diffResult.dispatchUpdatesTo(this)
  }

  fun addItems(items: List<Movie>) {
    movies.addAll(items)
    notifyDataSetChanged()
  }

  fun clear() {
    movies.clear()
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    return MovieViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
  }

  override fun getItemCount(): Int {
    return movies.size
  }

  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    holder.setItem(movies.get(position))
  }
}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  private val title: TextView by lazy { itemView.findViewById<TextView>(R.id.title) }

  fun setItem(item: Movie) {
    title.setText(item.title)
  }
}
