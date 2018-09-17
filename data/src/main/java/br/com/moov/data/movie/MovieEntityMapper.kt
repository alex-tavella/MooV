package br.com.moov.data.movie

import br.com.moov.data.movie.tmdb.TMDBMovie
import br.com.moov.domain.movie.Movie

class MovieEntityMapper {
  fun map(tmdbMovie: TMDBMovie): Movie = Movie(tmdbMovie.id, tmdbMovie.title)
}
