package br.com.alex.moov.androidapp

import br.com.alex.moov.androidapp.home.HomeActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

  fun inject(homeActivity: HomeActivity)
}