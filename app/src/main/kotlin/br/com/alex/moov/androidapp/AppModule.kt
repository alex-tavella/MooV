package br.com.alex.moov.androidapp

import android.app.Application
import android.content.Context
import br.com.alex.moov.TMDBApiKeyHolder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(application: Application, val apiTMDBApiKeyHolder: TMDBApiKeyHolder) {

  private val appContext: Context = application

  @Provides
  @Singleton
  @AppContext
  internal fun provideAppContext(): Context {
    return appContext
  }

  @Provides
  @Singleton
  internal fun provideApiKeyHolder(): TMDBApiKeyHolder {
    return apiTMDBApiKeyHolder
  }
}