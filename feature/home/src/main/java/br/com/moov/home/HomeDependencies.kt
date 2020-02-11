package br.com.moov.home

import br.com.moov.movies.navigation.MoviesNavigator

interface HomeDependencies {
    fun moviesNavigator(): MoviesNavigator
}
