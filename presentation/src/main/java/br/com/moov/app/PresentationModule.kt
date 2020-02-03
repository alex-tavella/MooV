package br.com.moov.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.moov.app.core.DefaultViewModelProviderFactory
import br.com.moov.app.moviedetail.MovieDetailViewModel
import br.com.moov.app.movies.MoviesViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY_GETTER
import kotlin.annotation.AnnotationTarget.PROPERTY_SETTER
import kotlin.reflect.KClass

@Target(FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER)
@Retention(RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
interface PresentationModule {

  @[Binds IntoMap ViewModelKey(MoviesViewModel::class)]
  fun bindsMoviesViewModel(moviesViewModel: MoviesViewModel): ViewModel

  @[Binds IntoMap ViewModelKey(MovieDetailViewModel::class)]
  fun bindsMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

  @[Binds Singleton]
  fun bindsViewModelFactory(factory: DefaultViewModelProviderFactory): ViewModelProvider.Factory
}
