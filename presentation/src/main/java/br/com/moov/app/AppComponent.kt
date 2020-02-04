package br.com.moov.app

import android.content.Context
import br.com.moov.app.moviedetail.MovieDetailActivity
import br.com.moov.app.movies.MoviesFragment
import br.com.moov.data.DataModule
import br.com.moov.domain.DomainModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DomainModule::class,
        DataModule::class,
        PresentationModule::class]
)
interface AppComponent {

    fun inject(movieDetailActivity: MovieDetailActivity)
    fun inject(moviesFragment: MoviesFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}

interface AppComponentProvider {
    val appComponent: AppComponent
}
